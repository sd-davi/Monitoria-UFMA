package com.example.smu.controller.Dto;

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

    //public CursoDto(Integer id, String nome) {this.id = id; this.nome = nome;}
}
