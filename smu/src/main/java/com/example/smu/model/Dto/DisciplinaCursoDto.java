package com.example.smu.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DisciplinaCursoDto {

    Integer disciplinaId;
    String disciplinaNome;
    Integer cursoId;

    public DisciplinaCursoDto(Integer cursoId, Integer disciplinaId, String nome) {
        this.cursoId = cursoId;
        this.disciplinaId = disciplinaId;
        this.disciplinaNome = nome;
    }
}
