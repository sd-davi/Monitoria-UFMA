package com.example.smu.controller.Dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.smu.model.Monitoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonitoriasPorAluno {
    private Integer id;
    private String nome;
    private String monitor;
    private Set<UsuarioDto> alunos;

    public MonitoriasPorAluno(Monitoria monitoria){
        this.id = monitoria.getId();
        this.nome = monitoria.getNome();
        this.monitor = monitoria.getMonitor().getNome();
        this.alunos = monitoria.getAlunos().stream().map(UsuarioDto::new).collect(Collectors.toSet());
    }
}
