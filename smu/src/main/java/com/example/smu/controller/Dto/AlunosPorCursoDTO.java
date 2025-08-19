package com.example.smu.controller.Dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.smu.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlunosPorCursoDTO {
    private Integer id;
    private String nome;
    private String curso;

    public AlunosPorCursoDTO(Usuario aluno){
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.curso = aluno.getCurso_aluno().getNome();
    }
}
