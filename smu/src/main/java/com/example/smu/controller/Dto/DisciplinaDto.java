package com.example.smu.controller.Dto;


import com.example.smu.model.Disciplina;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisciplinaDto {
    private Integer id;
    private String nome;
    private String codigo;
    private Integer idCurso;


    public DisciplinaDto (Disciplina disciplina) {
        this.id = disciplina.getId();
        this.nome = disciplina.getNome();
        this.codigo = disciplina.getCodigo();
        
    }
}
