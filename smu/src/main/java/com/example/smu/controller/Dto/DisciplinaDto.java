package com.example.smu.controller.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DisciplinaDto {
    private String nome;
    private String codigo;
    private Integer idCurso;
}
