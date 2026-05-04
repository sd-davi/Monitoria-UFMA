package com.example.smu.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class DisciplinaCursoDto {

    private Integer cursoId;
    private Integer disciplinaId;
    private String disciplinaNome;

    public DisciplinaCursoDto(Integer cursoId, Integer disciplinaId, String disciplinaNome) {
        this.cursoId = cursoId;
        this.disciplinaId = disciplinaId;
        this.disciplinaNome = disciplinaNome;
        
        
    }
}
