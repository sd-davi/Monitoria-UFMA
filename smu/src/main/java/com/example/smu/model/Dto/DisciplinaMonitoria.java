package com.example.smu.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DisciplinaMonitoria {
    Integer monitoriaId;
    String monitoriaNome;
    Integer disciplinaId;

    public DisciplinaMonitoria(Integer disciplinaId, Integer monitoriaId, String monitoriaNome) {
        this.disciplinaId = disciplinaId;
        this.monitoriaId = monitoriaId;
        this.monitoriaNome = monitoriaNome;
    }

}
