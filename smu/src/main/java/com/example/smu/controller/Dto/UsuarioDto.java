package com.example.smu.controller.Dto;
import java.time.LocalDateTime;

import com.example.smu.model.TipoUsuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    
    String nome;
    String email;
    String senha;
    LocalDateTime dataNascimento;
    String matricula;
    TipoUsuario tipo;
    Integer idCurso;
}
