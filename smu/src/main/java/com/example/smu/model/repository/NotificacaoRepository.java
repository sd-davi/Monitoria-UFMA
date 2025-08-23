package com.example.smu.model.repository;

import com.example.smu.model.Notificacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
public interface NotificacaoRepository  extends 
JpaRepository<Notificacao, Integer> {

    // Listar todas as notificações de um usuário específico
    List<Notificacao> findByUsuarioDestino_UsuarioId(Integer usuarioId);

    // Listar notificações não lidas de um usuário
    List<Notificacao> findByUsuarioDestino_UsuarioIdAndLidaFalse(Integer usuarioId);

}