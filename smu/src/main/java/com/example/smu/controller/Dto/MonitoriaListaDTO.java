package com.example.smu.controller.Dto;

import com.example.smu.model.Monitoria;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonitoriaListaDTO {
    private String nome;
    private String monitor;
    private String disciplina;
    private String curso;


    public MonitoriaListaDTO(Monitoria m){
        this.nome  = m.getNome();
        this.monitor = m.getMonitor().getNome();
        this.disciplina = m.getDisciplina().getNome();
        this.curso = m.getCurso().getNome();

    }
}
