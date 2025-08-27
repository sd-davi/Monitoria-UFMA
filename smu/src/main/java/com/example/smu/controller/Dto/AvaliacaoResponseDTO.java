package com.example.smu.controller.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliacaoResponseDTO {
    private Integer avaliacaoId;
    private Integer estrelas;
    private String comentario;
    private Integer sessaoId;
    private Integer alunoId;
    private String alunoNome;
}
