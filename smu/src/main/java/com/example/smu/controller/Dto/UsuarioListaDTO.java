package com.example.smu.controller.Dto;

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
    
    public UsuarioListaDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.matricula = usuario.getMatricula();
    }

}
