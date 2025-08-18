package com.example.smu.model;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name= "monitoria")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Monitoria {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_monitoria")
    Integer id;
    String nome;

    @Column(name = "dias_da_semana", length = 50)
    private String diasDaSemana;

    @Column
    private LocalTime horario;

    private String link;
    // curso
    @ManyToOne
    @JoinColumn(name= "curso_id")
    Curso curso;

    //sessões
    @OneToMany(mappedBy = "monitoria")
    @Builder.Default
    Set<Sessao> sessoes = new HashSet<>();;

    // disciplina
    @ManyToOne
    @JoinColumn(name="disciplina_id")
    Disciplina disciplina;

    // monitor
    @ManyToOne
    @JoinColumn(name = "monitor_id")
    Usuario monitor;

    // aluno
    @ManyToMany(mappedBy="monitorias")
    @Builder.Default
    Set <Usuario> alunos =  new HashSet<>();;

    
}
