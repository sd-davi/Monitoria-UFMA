package com.example.smu.controller.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class NotificacaoRequestDTO {
    private String mensagem;
    private Long usuarioDestinoId;
}
