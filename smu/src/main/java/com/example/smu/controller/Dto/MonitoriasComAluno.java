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
public class MonitoriasComAluno {

    private Integer id;
    private String nome;
    private String monitor;
    private Set<UsuarioDto> alunos;

    public MonitoriasComAluno(Monitoria m){
        this.id = m.getId();
        this.nome = m.getNome();
        this.monitor = m.getMonitor().getNome();
        this.alunos = m.getAlunos().stream()
                                .map(UsuarioDto::new)
                                .collect(Collectors.toSet());

    }
}
