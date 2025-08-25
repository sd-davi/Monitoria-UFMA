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
public class AlunosPorDisciplinaDTO {
    private Integer id;
    private String nome;
    private String email;
    private String curso;
    private Set<DisciplinaDto> disciplinas;
    private Set<MonitoriaDto> monitorias;

    public AlunosPorDisciplinaDTO (Usuario aluno) {
        this.id = aluno.getId();
        this.nome = aluno.getNome();
        this.email = aluno.getEmail();
        this.curso = aluno.getCurso().getNome();
        // Converte a lista de entidades Disciplina para uma lista de DisciplinaDTO
        this.disciplinas = aluno.getDisciplinas().stream()
                                  .map(DisciplinaDto::new)
                                  .collect(Collectors.toSet());
        this.monitorias = aluno.getMonitorias().stream().map(MonitoriaDto::new).collect(Collectors.toSet());
    }
}
