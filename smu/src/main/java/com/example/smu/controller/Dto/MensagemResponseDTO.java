package com.example.smu.controller.Dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MensagemResponseDTO {
    private Integer mensagemId;
    private String conteudo;
    private LocalDateTime dataHora;
    private Integer autorId;
    private String autorNome;
    private Integer sessaoId;
}
