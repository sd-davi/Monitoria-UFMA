package com.example.smu.controller.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonitoriaDto {
    String nome;
    Integer idMonitor;
    Integer idCurso;
    Integer idDisciplina;
}
