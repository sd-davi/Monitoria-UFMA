package com.example.smu.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smu.model.Notificacao;
public interface NotificacaoRepository  extends 
JpaRepository<Notificacao, Integer> {

    // Listar todas as notificações de um usuário específico
    List<Notificacao> findByDestinatario_Id(Integer usuarioId);

    // Listar notificações não lidas de um usuário
    List<Notificacao> findByDestinatario_IdAndLidaFalse(Integer usuarioId);

}