// src/main/java/br/edu/ifpb/ifitspring/dto/AuthResponse.java
package br.edu.ifpb.ifitspring.dto;
import lombok.*;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor
public class AuthResponse {
    private String token;
    private Long id;
    private String nome;
    private String email;
    private String role;
}
