package com.example.smu.controller.Dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.smu.model.Curso;
import com.example.smu.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoComAlunosDTO {
    private Integer id;
    private String nome;
    private Set<UsuarioDto> alunos;
    private Set<DisciplinaDto> disciplinas;

    public CursoComAlunosDTO(Curso curso){
        this.id = curso.getId();
        this.nome = curso.getNome();

        this.alunos =  curso.getAlunos().stream()
                            .map(UsuarioDto::new).collect(Collectors.toSet());

        this.disciplinas = curso.getDisciplinas().stream()
        .map(DisciplinaDto::new).collect(Collectors.toSet());
    }
}
