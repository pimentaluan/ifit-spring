package br.edu.ifpb.ifitspring.controller;

import br.edu.ifpb.ifitspring.model.Usuario;
import br.edu.ifpb.ifitspring.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth") // <<<<<< DESAMBIGUA AS ROTAS
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioPublicController {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;

    @PostMapping("/usuarios") // vira POST /auth/usuarios (ou troque para "/signup")
    public Usuario criar(@RequestBody Usuario u) {
        if (u.getSenha() != null && !u.getSenha().isBlank()) {
            u.setSenha(encoder.encode(u.getSenha()));
        }
        if (u.getRole() == null || u.getRole().isBlank()) {
            u.setRole("ROLE_USER");
        }
        return repo.save(u);
    }
}
