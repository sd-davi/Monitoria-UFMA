package com.example.smu.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_usuario")
    Integer id;

    String nome;

    //@Column(unique = true)
    String email;
    String senha;
    LocalDate dataNascimento;
    String matricula;

    @Enumerated(EnumType.STRING)
    TipoUsuario tipo;

    // curso
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    // monitoria 
    @ManyToMany
    @JoinTable(
        name= "usuario_monitoria",
        joinColumns= @JoinColumn(name="usuario_id"),
        inverseJoinColumns = @JoinColumn(name="monitoria_id")
    )
    @Builder.Default
    Set<Monitoria> monitorias = new HashSet<>();

    // disciplina

    @ManyToMany
    @JoinTable(
        name= "usuario_disciplina",
        joinColumns = @JoinColumn(name="usuario_id"),
        inverseJoinColumns= @JoinColumn(name= "disciplina_id")
    )
    @Builder.Default
    Set<Disciplina> disciplinas = new HashSet<>();

    // sessão
    @ManyToMany
    @JoinTable(
        name= "usuario_sessao",
        joinColumns = @JoinColumn(name="usuario_id"),
        inverseJoinColumns= @JoinColumn(name= "sessao_id")
    )
    @Builder.Default
    Set<Sessao> sessoes = new HashSet<>();

    //mensagem

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    Set<Mensagem> mensagens =  new HashSet<>();

    // notificação

    @OneToMany(mappedBy = "destinatario", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    Set<Notificacao> notificacoes = new HashSet<>();

    //mensagem
    
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    Set<Avaliacao> avaliacoes = new HashSet<>();
}
