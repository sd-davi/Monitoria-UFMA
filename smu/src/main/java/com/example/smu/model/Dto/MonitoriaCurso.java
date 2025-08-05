package com.example.smu.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MonitoriaCurso {
    Integer monitoriaId;
    String monitoriaNome;
    Integer cursoId;

    public MonitoriaCurso(Integer cursoId, Integer monitoriaId, String monitoriaNome) {
        this.cursoId = cursoId;
        this.monitoriaId = monitoriaId;
        this.monitoriaNome = monitoriaNome;
    }
}
