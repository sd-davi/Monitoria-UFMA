package com.example.smu.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class MonitoriaAlunoDto {
    private Integer alunoId;
    private String alunoNome;
    private Integer monitoriaId;
    private String monitoriaNome;
    private String nomeDisciplina; // Inverti para bater com a Query
    private String nomeMonitor;

    public MonitoriaAlunoDto(Integer alunoId, String alunoNome, Integer monitoriaId, String monitoriaNome, String nomeDisciplina, String nomeMonitor){
        this.alunoId = alunoId;
        this.alunoNome = alunoNome; // Adicionado!
        this.monitoriaId = monitoriaId;
        this.monitoriaNome = monitoriaNome;
        this.nomeDisciplina = nomeDisciplina;
        this.nomeMonitor = nomeMonitor;
    }
    // Este é o construtor NOVO para essa query de 4 campos
    public MonitoriaAlunoDto(Integer alunoId, String alunoNome, Integer monitoriaId, String monitoriaNome) {
        this.alunoId = alunoId;
        this.alunoNome = alunoNome;
        this.monitoriaId = monitoriaId;
        this.monitoriaNome = monitoriaNome;
    }
}