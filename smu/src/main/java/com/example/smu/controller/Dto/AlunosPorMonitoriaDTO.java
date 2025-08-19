package com.example.smu.controller.Dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.smu.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlunosPorMonitoriaDTO {
    private Integer id;
    private String nome;
    private Set<MonitoriaDto> monitorias;

    public AlunosPorMonitoriaDTO(Usuario aluno){
        this.id = aluno.getId();
        this.nome = aluno.getNome();

        this.monitorias = aluno.getMonitorias().stream().map(MonitoriaDto::new).collect(Collectors.toSet());
    }
}
