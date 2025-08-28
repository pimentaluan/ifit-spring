package br.edu.ifpb.ifitspring.controller;

import br.edu.ifpb.ifitspring.model.Treino;
import br.edu.ifpb.ifitspring.repository.TreinoRepository;
import br.edu.ifpb.ifitspring.repository.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/treinos")
@CrossOrigin(origins = "http://localhost:4200")
public class TreinoController {

    private final TreinoRepository treinoRepo;
    private final UsuarioRepository usuarioRepo;

    public TreinoController(TreinoRepository treinoRepo, UsuarioRepository usuarioRepo) {
        this.treinoRepo = treinoRepo;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping("/detalhe/{treinoId}")
    public ResponseEntity<Treino> buscarTreino(@PathVariable Long treinoId) {
        return treinoRepo.findById(treinoId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{usuarioId}")
    public ResponseEntity<Treino> criarTreino(@PathVariable Long usuarioId, @RequestBody Treino treino) {
        return usuarioRepo.findById(usuarioId).map(user -> {
            treino.setUsuario(user);
            return ResponseEntity.ok(treinoRepo.save(treino));
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<Treino>> listarTreinos(@PathVariable Long usuarioId) {
        if (!usuarioRepo.existsById(usuarioId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(treinoRepo.findByUsuarioId(usuarioId));
    }


    @DeleteMapping("/delete/{treinoId}")
    public ResponseEntity<Void> deletarTreino(@PathVariable Long treinoId) {
        if (!treinoRepo.existsById(treinoId)) {
            return ResponseEntity.notFound().build();
        }
        treinoRepo.deleteById(treinoId);
        return ResponseEntity.noContent().build();
    }

}
