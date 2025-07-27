package com.example.smu.model;

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

    
    // curso
    @ManyToOne
    @JoinColumn(name= "curso_id")
    Curso curso;

    //sessões
    @OneToMany(mappedBy = "monitoria")
    Set<Sessao> sessoes;

    // disciplina
    @OneToOne
    @JoinColumn(name="disciplina_id")
    Disciplina disciplina;

    // monitor
    @ManyToOne
    @JoinColumn(name = "monitor_id")
    Usuario monitor;

    // aluno
    @ManyToMany(mappedBy="monitorias")
    Set <Usuario> alunos;

    
}
