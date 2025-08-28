package br.edu.ifpb.ifitspring.controller;

import br.edu.ifpb.ifitspring.model.Usuario;
import br.edu.ifpb.ifitspring.repository.UsuarioRepository;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.*;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UsuarioController {
    private final UsuarioRepository repo;
    public UsuarioController(UsuarioRepository repo){ this.repo = repo; }

    @GetMapping("/usuarios")
    public List<Usuario> listar(){ return repo.findAll(); }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<Usuario> porId(@PathVariable Long id){
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> criarOuAtualizar(@RequestBody Usuario u) {
        return repo.findByEmail(u.getEmail())
                .map(existente -> {
                    // Atualiza só os campos que podem mudar
                    existente.setNome(u.getNome());
                    existente.setIdade(u.getIdade());
                    return ResponseEntity.ok(repo.save(existente));
                })
                .orElseGet(() -> {
                    // Novo usuário
                    return ResponseEntity.ok(repo.save(u));
                });
    }


    @DeleteMapping("/usuarios/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
