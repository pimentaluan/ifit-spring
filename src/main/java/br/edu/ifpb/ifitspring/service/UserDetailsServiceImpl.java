package br.edu.ifpb.ifitspring.service;

import br.edu.ifpb.ifitspring.model.Usuario;
import br.edu.ifpb.ifitspring.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // aqui username = email
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // Se role é String (ex.: "ROLE_USER"), NÃO use .name()
        List<GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(usuario.getRole()));

        return new User(
                usuario.getEmail(),
                usuario.getSenha(),
                authorities
        );
    }
}
