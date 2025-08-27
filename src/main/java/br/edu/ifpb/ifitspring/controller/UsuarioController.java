package br.edu.ifpb.ifitspring.controller;

import br.edu.ifpb.ifitspring.dto.DiaTreinoDTO;
import br.edu.ifpb.ifitspring.dto.UsuarioDTO;
import br.edu.ifpb.ifitspring.model.Usuario;
import br.edu.ifpb.ifitspring.repository.UsuarioRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;
    private final ObjectMapper mapper = new ObjectMapper();

    private boolean isAdmin(Authentication auth) {
        return auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    // ADMIN cria usuário (usuário comum usa /auth/register)
    @PostMapping("/usuarios")
    public UsuarioDTO criar(@RequestBody UsuarioDTO dto) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!isAdmin(auth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        if (repo.existsByEmail(dto.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }

        String senhaHash = (dto.getSenha() != null && !dto.getSenha().isBlank())
                ? encoder.encode(dto.getSenha())
                : encoder.encode("troque-sua-senha");

        Usuario u = Usuario.builder()
                .nome(dto.getNome())
                .email(dto.getEmail())
                .senha(senhaHash)
                .objetivo(dto.getObjetivo())
                .idade(dto.getIdade())
                .frequencia(dto.getFrequencia())
                .nivel(dto.getNivel())
                .lesao(dto.getLesao())
                .local(dto.getLocal())
                .treinoJson(mapper.writeValueAsString(dto.getTreino()))
                .role(dto.getRole() != null ? dto.getRole() : "ROLE_USER")
                .build();

        u = repo.save(u);
        dto.setId(u.getId());
        dto.setSenha(null); // nunca retornar senha
        return dto;
    }

    // ADMIN lista todos
    @GetMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UsuarioDTO> listar(Authentication auth) {
        if (!isAdmin(auth)) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return repo.findAll().stream().map(u -> UsuarioDTO.builder()
                .id(u.getId())
                .nome(u.getNome())
                .email(u.getEmail())
                .objetivo(u.getObjetivo())
                .idade(u.getIdade())
                .frequencia(u.getFrequencia())
                .nivel(u.getNivel())
                .lesao(u.getLesao())
                .local(u.getLocal())
                .build()
        ).collect(Collectors.toList());
    }

    // ADMIN vê qualquer; USER vê apenas o próprio
    @GetMapping("/usuarios/{id}")
    public UsuarioDTO porId(@PathVariable Long id, Authentication auth) throws Exception {
        Usuario u = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        boolean admin = isAdmin(auth);
        boolean dono = u.getEmail().equalsIgnoreCase(auth.getName());
        if (!admin && !dono) throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        List<DiaTreinoDTO> treino = mapper.readValue(
                u.getTreinoJson() == null ? "[]" : u.getTreinoJson(),
                new TypeReference<List<DiaTreinoDTO>>() {}
        );

        return UsuarioDTO.builder()
                .id(u.getId())
                .nome(u.getNome())
                .email(u.getEmail())
                .objetivo(u.getObjetivo())
                .idade(u.getIdade())
                .frequencia(u.getFrequencia())
                .nivel(u.getNivel())
                .lesao(u.getLesao())
                .local(u.getLocal())
                .treino(treino)
                .build();
    }

    // USER pega seu próprio registro completo
    @GetMapping("/me")
    public UsuarioDTO me(Authentication auth) throws Exception {
        Usuario u = repo.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        List<DiaTreinoDTO> treino = mapper.readValue(
                u.getTreinoJson() == null ? "[]" : u.getTreinoJson(),
                new TypeReference<List<DiaTreinoDTO>>() {}
        );

        return UsuarioDTO.builder()
                .id(u.getId())
                .nome(u.getNome())
                .email(u.getEmail())
                .objetivo(u.getObjetivo())
                .idade(u.getIdade())
                .frequencia(u.getFrequencia())
                .nivel(u.getNivel())
                .lesao(u.getLesao())
                .local(u.getLocal())
                .treino(treino)
                .build();
    }

    // USER salva/atualiza o próprio treino (usado pelo formulário)
    @PutMapping("/me/treino")
    public UsuarioDTO salvarTreino(@RequestBody List<DiaTreinoDTO> treino, Authentication auth) throws Exception {
        Usuario u = repo.findByEmail(auth.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        u.setTreinoJson(mapper.writeValueAsString(treino));
        u = repo.save(u);

        return UsuarioDTO.builder()
                .id(u.getId())
                .nome(u.getNome())
                .email(u.getEmail())
                .objetivo(u.getObjetivo())
                .idade(u.getIdade())
                .frequencia(u.getFrequencia())
                .nivel(u.getNivel())
                .lesao(u.getLesao())
                .local(u.getLocal())
                .treino(treino)
                .build();
    }
}
