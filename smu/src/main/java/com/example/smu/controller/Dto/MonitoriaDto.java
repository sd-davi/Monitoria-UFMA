package com.example.smu.controller.Dto;


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
    Integer idMonitor;
    Integer idCurso;
    Integer idDisciplina;

    public MonitoriaDto (Monitoria monitoria) {
        this.id = monitoria.getId();
        this.nome = monitoria.getNome();
    }
}
