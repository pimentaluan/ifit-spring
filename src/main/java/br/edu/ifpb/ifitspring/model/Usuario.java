// src/main/java/br/edu/ifpb/ifitspring/model/Usuario.java
package br.edu.ifpb.ifitspring.model;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name="usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(nullable=false)
    private String email;

    @Column(nullable=false)
    private String senha; // BCrypt

    private String objetivo;
    private Integer idade;
    private Integer frequencia;
    private String nivel;
    private String lesao;
    private String local;

    @Column(columnDefinition = "TEXT")
    private String treinoJson;

    // "ROLE_USER" ou "ROLE_ADMIN"
    @Column(nullable=false)
    private String role;
}
