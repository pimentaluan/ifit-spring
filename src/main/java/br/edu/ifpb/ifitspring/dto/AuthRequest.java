package br.edu.ifpb.ifitspring.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class AuthRequest {
    private String email;

    // aceita {"senha":"..."} ou {"password":"..."}
    @JsonAlias({"password", "senha"})
    private String senha;
}
