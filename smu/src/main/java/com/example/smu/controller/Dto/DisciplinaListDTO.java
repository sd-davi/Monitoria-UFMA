package com.example.smu.controller.Dto;
import com.example.smu.model.Disciplina;

import lombok.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisciplinaListDTO {
    private String nome;
    private String codigo;
    private String curso;


    public DisciplinaListDTO(Disciplina d){
        this.nome = d.getNome();
        this.codigo = d.getCodigo();
        this.curso = d.getCurso().getNome();
    }
}
