package com.example.smu.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Builder.Default
    @JsonBackReference
    Set <Usuario> alunos = new HashSet<>();;

    //monitoria
    @OneToMany(mappedBy = "disciplina")
    @Builder.Default
    Set<Monitoria> monitorias = new HashSet<>();

    
    // curso
    @ManyToOne
    @JoinColumn(name="curso_id")
    @JsonBackReference
    Curso curso;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disciplina disciplina = (Disciplina) o;
        // Só compara pela identidade se o ID não for nulo (entidade já persistida)
        return id != null && Objects.equals(id, disciplina.id);
    }

    @Override
    public int hashCode() {
        // Usa um valor fixo se a entidade for nova, ou o hash do ID se já existir
        return id != null ? id.hashCode() : System.identityHashCode(this);
    }

}
