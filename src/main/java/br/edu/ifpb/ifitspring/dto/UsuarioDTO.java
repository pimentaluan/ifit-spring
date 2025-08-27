// src/main/java/br/edu/ifpb/ifitspring/dto/UsuarioDTO.java
package br.edu.ifpb.ifitspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.util.List;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;

    // aceito no input, nunca exibido no JSON de resposta
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;

    private String objetivo;
    private Integer idade;
    private Integer frequencia;
    private String nivel;
    private String lesao;
    private String local;

    private List<DiaTreinoDTO> treino;

    // opcional: para o admin criar usuários com papel
    private String role; // "ROLE_USER" ou "ROLE_ADMIN"
}
