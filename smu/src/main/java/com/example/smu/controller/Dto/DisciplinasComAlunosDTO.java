package com.example.smu.controller.Dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.smu.model.Disciplina;

import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisciplinasComAlunosDTO {
    private Integer id;
    private String nome;
    private Set<UsuarioDto> alunos;

    // Construtores, Getters e Setters
    public DisciplinasComAlunosDTO(Disciplina disciplina) {
        this.id = disciplina.getId();
        this.nome = disciplina.getNome();
        // Converte a lista de entidades Aluno para uma lista de AlunoDTO
        this.alunos = disciplina.getAlunos().stream()
                                .map(UsuarioDto::new)
                                .collect(Collectors.toSet());
    }
}
