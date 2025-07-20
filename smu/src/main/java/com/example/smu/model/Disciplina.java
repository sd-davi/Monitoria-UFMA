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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="disciplina")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Disciplina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_disciplina")
    Integer id;
    String nome;
    String codigo;


    // aluno
    @ManyToMany(mappedBy="disciplinas")
    Set <Usuario> alunos;

    // curso
    @ManyToOne
    @JoinColumn(name="curso_id")
    Curso curso;


}
