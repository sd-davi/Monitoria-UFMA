package com.example.smu.model.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MonitoriaMonitorDto {
    Integer MonitorId;
    String monitorNome;
    Integer monitoriaId;
    String monitoriaNome;
}
