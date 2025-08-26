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
    private Long avaliacaoId;
    private Integer estrelas;
    private String comentario;
    private Long sessaoId;
    private Long alunoId;
    private String alunoNome;
}
