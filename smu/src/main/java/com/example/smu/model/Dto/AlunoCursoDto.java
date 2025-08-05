package com.example.smu.model.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class AlunoCursoDto {
    Integer alunoId;
    String alunoNome;
    Integer cursoId;

     public AlunoCursoDto(Integer cursoId, Integer alunoId, String nome) {
        this.cursoId = cursoId;
        this.alunoId = alunoId;
        this.alunoNome = nome;
    }
}
