package com.example.smu.controller.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MensagemRequestDTO {
    private String conteudo;
    private Long autorId;
    private Long sessaoId;
}
