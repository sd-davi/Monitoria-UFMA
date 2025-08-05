package com.example.smu.controller.Dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoDto {
    private String link;
    private LocalDateTime horario;
    private Integer idMonitoria;
}
