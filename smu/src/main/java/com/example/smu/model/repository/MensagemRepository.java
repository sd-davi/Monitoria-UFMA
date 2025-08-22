package com.example.smu.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smu.model.Mensagem;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {

    // Listar mensagens de uma sessão em ordem cronológica
   List<Mensagem> findBySessaoAgendada_SessaoIdOrderByDataHoraAsc(Integer sessaoId);

    // Listar mensagens enviadas por um usuário específico em uma sessão
    List<Mensagem> findBySessaoAgendada_SessaoIdAndAutor_UsuarioIdOrderByDataHoraAsc(Integer sessaoId, Integer usuarioId);
    
}
