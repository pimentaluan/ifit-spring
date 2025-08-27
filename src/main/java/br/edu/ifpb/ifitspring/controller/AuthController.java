// src/main/java/br/edu/ifpb/ifitspring/controller/AuthController.java
package br.edu.ifpb.ifitspring.controller;

import br.edu.ifpb.ifitspring.dto.AuthRequest;
import br.edu.ifpb.ifitspring.dto.AuthResponse;
import br.edu.ifpb.ifitspring.dto.UsuarioDTO;
import br.edu.ifpb.ifitspring.model.Usuario;
import br.edu.ifpb.ifitspring.repository.UsuarioRepository;
import br.edu.ifpb.ifitspring.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;
    private final ObjectMapper mapper = new ObjectMapper();

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@RequestBody UsuarioDTO dto) throws Exception {
        final String emailNorm = dto.getEmail() == null ? "" : dto.getEmail().trim().toLowerCase();
        if (repo.existsByEmail(emailNorm)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }
        Usuario u = Usuario.builder()
                .nome(dto.getNome())
                .email(emailNorm)                      // ← salva normalizado
                .senha(encoder.encode(dto.getSenha()))
                .objetivo(dto.getObjetivo())
                .idade(dto.getIdade())
                .frequencia(dto.getFrequencia())
                .nivel(dto.getNivel())
                .lesao(dto.getLesao())
                .local(dto.getLocal())
                .treinoJson(mapper.writeValueAsString(dto.getTreino()))
                .role("ROLE_USER")
                .build();
        u = repo.save(u);
        String token = jwt.generate(u.getEmail(), u.getRole(), u.getId());
        return AuthResponse.builder()
                .token(token).id(u.getId()).nome(u.getNome()).email(u.getEmail()).role(u.getRole())
                .build();
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest req) {
        final String emailNorm = req.getEmail() == null ? "" : req.getEmail().trim();
        final String senha = req.getSenha();

        Usuario u = repo.findByEmail(emailNorm)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas"));

        if (senha == null || senha.isBlank() || !encoder.matches(senha, u.getSenha())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciais inválidas");
        }

        String token = jwt.generate(u.getEmail(), u.getRole(), u.getId());
        return AuthResponse.builder()
                .token(token).id(u.getId()).nome(u.getNome()).email(u.getEmail()).role(u.getRole())
                .build();
    }

}
