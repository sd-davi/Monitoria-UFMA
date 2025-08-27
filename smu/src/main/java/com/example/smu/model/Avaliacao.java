package com.example.smu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "avaliacao")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer avaliacaoId;

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