package com.example.smu.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "sessao")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_sessao")
    private Integer id;
    
    LocalDateTime horario;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusSessao status;

    @ManyToOne
    @JoinColumn(name= "monitoria_id")
    Monitoria monitoria;

    // aluno
    @ManyToMany(mappedBy="sessoes")
    @Builder.Default
    Set <Usuario> alunos = new HashSet<>();

    //mensagem
    @OneToMany(mappedBy = "sessao")
    @Builder.Default
    Set<Mensagem> mensagens = new HashSet<>();

    //avaliação
    @OneToMany(mappedBy = "sessao", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    Set<Avaliacao> avaliacoes = new HashSet<>();

}
