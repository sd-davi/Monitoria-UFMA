package com.example.smu.controller.Dto;

import com.example.smu.model.Curso;
import com.example.smu.model.Disciplina;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoDto {
    Integer id;
    String codigo;
    String nome;

    public CursoDto(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
    }
}
