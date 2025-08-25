package com.example.smu.controller.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MonitoriaResponseDto {
    private Integer id;
    private String nome;
    private String cursoNome;
    private String disciplinaNome;
    private String monitorNome;
}
