package com.example.smu.controller.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvaliacaoRequestDTO {
    private Integer estrelas;
    private String comentario;
    private Long sessaoId;
    private Long alunoId;
}
