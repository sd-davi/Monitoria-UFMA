package com.example.smu.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "curso")
@Builder
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_curso")
    Integer id;
    String codigo;
    String nome;  

    // disciplina
    @OneToMany(mappedBy = "curso")
    @Builder.Default
    @JsonManagedReference
    Set<Disciplina> disciplinas = new HashSet<>();

    @OneToMany(mappedBy = "curso_aluno")
    @Builder.Default
    Set<Usuario> alunos = new HashSet<>();
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        // Só compara pela identidade se o ID não for nulo (entidade já persistida)
        return id != null && Objects.equals(id, curso.id);
    }

    @Override
    public int hashCode() {
        // Usa um valor fixo se a entidade for nova, ou o hash do ID se já existir
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }
}
