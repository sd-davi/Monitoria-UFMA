package com.example.smu.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smu.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {

    // Listar mensagens de uma sessão em ordem cronológica
   List<Mensagem> findBySessao_IdOrderByHorarioAsc(Integer sessaoId);

    // Listar mensagens enviadas por um usuário específico em uma sessão
    List<Mensagem> findBySessao_IdAndAutor_IdOrderByHorarioAsc(Integer sessaoId, Integer usuarioId);
    
}
