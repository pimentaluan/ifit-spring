package br.edu.ifpb.ifitspring.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ExerciciosController {

    @GetMapping("/exercicios")
    public Map<String, List<Exercicio>> listar() {
        Map<String, List<Exercicio>> data = new LinkedHashMap<>();

        data.put("Tórax", List.of(
                new Exercicio(1, "Supino Reto", "hipertrofia", "academia", 4, 10, "Controle a descida"),
                new Exercicio(2, "Flexão de Braço", "resistência", "casa", 4, 15, "Core firme")
        ));

        data.put("Costas", List.of(
                new Exercicio(3, "Puxada Frontal", "hipertrofia", "academia", 4, 10, "Pegada média"),
                new Exercicio(4, "Remada Curvada", "hipertrofia", "academia", 4, 8, "Coluna neutra")
        ));

        data.put("Pernas", List.of(
                new Exercicio(5, "Agachamento Livre", "hipertrofia", "academia", 4, 8, "Alinhamento de joelhos"),
                new Exercicio(6, "Avanço", "resistência", "casa", 3, 12, "Passadas alternadas")
        ));

        return data;
    }

    record Exercicio(
            Integer id, String nome, String objetivo, String local,
            Integer series, Integer reps, String instrucoes
    ) {}
}
