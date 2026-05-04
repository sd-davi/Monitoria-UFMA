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
    private String curso;
    private String nome;
    private String monitor;
    private String descricao;
    private String diasDaSemana;
    private java.time.LocalTime horario;
    private String link;
    private Set<UsuarioDto> alunos;

    public MonitoriasComAluno(Monitoria m){
        this.id = m.getId();
        this.curso = m.getCurso().getNome();
        this.nome = m.getNome();
        this.monitor = m.getMonitor().getNome();
        this.descricao = m.getDescricao();
        this.diasDaSemana = m.getDiasDaSemana();
        this.horario = m.getHorario();
        this.link = m.getLink();
        this.alunos = m.getAlunos().stream()
                                .map(UsuarioDto::new)
                                .collect(Collectors.toSet());

    }
}
