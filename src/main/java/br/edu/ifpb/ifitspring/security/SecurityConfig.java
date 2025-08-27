// src/main/java/br/edu/ifpb/ifitspring/config/SecurityConfig.java
package br.edu.ifpb.ifitspring.config;

import br.edu.ifpb.ifitspring.security.JwtAuthFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwt;

    public SecurityConfig(JwtAuthFilter jwt) { this.jwt = jwt; }

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.cors(Customizer.withDefaults());
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/auth/**").permitAll()            // ← primeiro e bem explícito
                .requestMatchers(HttpMethod.GET, "/exercicios/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/usuarios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,  "/usuarios").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,  "/usuarios/**").authenticated()
                .requestMatchers(HttpMethod.GET,  "/me").authenticated()
                .requestMatchers(HttpMethod.PUT,  "/me/treino").authenticated()
                .anyRequest().authenticated()
        );

        // respostas claras pra diferenciar 401 x 403
        http.exceptionHandling(ex -> ex
                .authenticationEntryPoint((req, res, e) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .accessDeniedHandler((req, res, e) -> res.sendError(HttpServletResponse.SC_FORBIDDEN))
        );

        http.addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
