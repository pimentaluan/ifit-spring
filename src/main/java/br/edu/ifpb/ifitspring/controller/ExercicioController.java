package br.edu.ifpb.ifitspring.controller;

import br.edu.ifpb.ifitspring.model.Exercicio;
import br.edu.ifpb.ifitspring.repository.ExercicioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/exercicios")
public class ExercicioController {

    private final ExercicioRepository repo;

    public ExercicioController(ExercicioRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<Exercicio> listar() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercicio> buscar(@PathVariable Long id) {
        return repo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Exercicio> criar(@RequestBody Exercicio e) {
        e.setId(null);
        return ResponseEntity.ok(repo.save(e));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repo.existsById(id)) return ResponseEntity.notFound().build();
        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
