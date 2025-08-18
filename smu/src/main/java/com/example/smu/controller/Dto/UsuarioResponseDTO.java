package com.example.smu.controller.Dto;

import java.time.LocalDate;

import com.example.smu.model.TipoUsuario;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UsuarioResponseDTO {
    private Integer id;
    private String nome;
    private String email;
    private LocalDate dataNascimento;
    private String matricula;
    private TipoUsuario tipoUsuario;
    private Integer cursoId;
    private String cursoNome;
}