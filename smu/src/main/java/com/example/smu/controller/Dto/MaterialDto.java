package com.example.smu.controller.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MaterialDto {
    private String titulo;
    private String descricao;
    private String link;
    private String arquivo;
    private Integer idMonitoria;
}

