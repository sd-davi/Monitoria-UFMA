package com.example.smu.controller.Dto;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.smu.model.TipoUsuario;
import com.example.smu.model.Usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    
    Integer id;
    String nome;
    String email;
    String senha;
    LocalDate dataNascimento;
    String matricula;
    TipoUsuario tipo;
    Integer idCurso;

    public UsuarioDto (Usuario aluno) {
        this.id = aluno.getId();
        this.nome = aluno.getNome();
    }
}
