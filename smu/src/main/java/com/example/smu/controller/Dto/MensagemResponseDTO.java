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
    private Long mensagemId;
    private String conteudo;
    private LocalDateTime dataHora;
    private Long autorId;
    private String autorNome;
    private Long sessaoId;
}
