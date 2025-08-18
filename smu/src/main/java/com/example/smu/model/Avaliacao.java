package com.example.smu.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "avaliacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avaliacaoId;

    @Column(nullable = false)
    private Integer estrelas;  // Valor entre 1 e 5

    @Column(columnDefinition = "TEXT")
    private String comentario;

    // ---------- RELACIONAMENTOS ----------

    @ManyToOne
    @JoinColumn(name = "sessao", nullable = false)
    private Sessao sessao;

    @ManyToOne
    @JoinColumn(name = "aluno", nullable = false)
    private Usuario aluno;
}