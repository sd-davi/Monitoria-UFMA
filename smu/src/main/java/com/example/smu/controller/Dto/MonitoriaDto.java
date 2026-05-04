package com.example.smu.controller.Dto;


import java.time.LocalTime;

import com.example.smu.model.Monitoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonitoriaDto {

    Integer id;
    String nome;
    String descricao;
    Integer idMonitor;
    Integer idCurso;
    Integer idDisciplina;
    String diasDaSemana;
    LocalTime horario; 
    String link;

    public MonitoriaDto (Monitoria monitoria) {
        this.id = monitoria.getId();
        this.nome = monitoria.getNome();
    }
}
