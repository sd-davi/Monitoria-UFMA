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
    
    private Integer id;
    private String nome;
    private String email;
    private String senha;
    private LocalDate dataNascimento;
    private String matricula;
    private TipoUsuario tipo;
    private Integer idCurso; 

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.tipo = usuario.getTipo();
        this.matricula = usuario.getMatricula();
        this.dataNascimento = usuario.getDataNascimento();
        if (usuario.getCurso() != null) {
            this.idCurso = usuario.getCurso().getId();
        }
    }
}
