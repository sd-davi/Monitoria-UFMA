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
public class NotificacaoResponseDTO {
    private Long notificacaoId;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private boolean lida;
    private Long usuarioDestinoId;
    private String usuarioDestinoNome;
}
