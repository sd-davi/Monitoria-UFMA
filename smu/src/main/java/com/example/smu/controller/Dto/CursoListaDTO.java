package com.example.smu.controller.Dto;

import com.example.smu.model.Curso;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CursoListaDTO {
    private String nome;
    private String codigo;

    public CursoListaDTO(Curso c){
        this.nome = c.getNome();
        this.codigo = c.getCodigo();
    }

}
