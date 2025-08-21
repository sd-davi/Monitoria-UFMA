package com.example.smu.controller.Dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.example.smu.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioListaDTO {
    private Integer id;
    private String nome;
    private String email;
    private String matricula;
    private String curso;
    private Set<MonitoriaDto> monitorias;
    
    public UsuarioListaDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.matricula = usuario.getMatricula();
        this.curso = usuario.getCurso().getNome();
        this.monitorias = usuario.getMonitorias().stream().map(MonitoriaDto::new).collect(Collectors.toSet());
    }

}
