package br.edu.ifpb.ifitspring.dto;

import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DiaTreinoDTO {
    private String parte;
    private List<Integer> exercicios;
}
