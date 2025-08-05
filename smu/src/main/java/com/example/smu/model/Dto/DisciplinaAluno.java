package com.example.smu.model.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class DisciplinaAluno {
    Integer alunoId;
    String alunoNome;
    Integer disciplinaId;
    String disciplinaNome;
    
     public DisciplinaAluno(Integer discId, Integer alunoId, String discnome, String alnome) {
        this.disciplinaId = discId;
        this.alunoId = alunoId;
        this.alunoNome = alnome;
        this.disciplinaNome = discnome;
    }
}
