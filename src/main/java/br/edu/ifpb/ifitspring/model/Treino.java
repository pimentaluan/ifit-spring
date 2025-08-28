package br.edu.ifpb.ifitspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "treinos")
public class Treino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parte;
    private String objetivo;
    private Integer frequencia;
    private String nivel;
    private String lesao;
    private String local;

    @Column(columnDefinition = "TEXT")
    private String exerciciosJson;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @JsonBackReference
    private Usuario usuario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getParte() { return parte; }
    public void setParte(String parte) { this.parte = parte; }

    public String getExerciciosJson() { return exerciciosJson; }
    public void setExerciciosJson(String exerciciosJson) { this.exerciciosJson = exerciciosJson; }


    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public Integer getFrequencia() { return frequencia; }
    public void setFrequencia(Integer frequencia) { this.frequencia = frequencia; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public String getLesao() { return lesao; }
    public void setLesao(String lesao) { this.lesao = lesao; }

    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }

}
