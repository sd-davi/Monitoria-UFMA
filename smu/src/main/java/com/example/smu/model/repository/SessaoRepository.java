package com.example.smu.model.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smu.model.Sessao;
import com.example.smu.model.StatusSessao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface  SessaoRepository extends JpaRepository<Sessao,Integer> {

    List<Sessao> findByMonitoriaId(Integer monitoriaId);

    // Listar Sessões de um Aluno
    @Query("""
    SELECT s FROM Sessao s
    JOIN s.alunos a
    WHERE a.id = :alunoId
    """)
    List<Sessao> findSessoesByAlunoId(@Param("alunoId") Integer alunoId);

    // Listar todas as sessões de um aluno específico
    List<Sessao> findByAluno_UsuarioId(Integer alunoId);

    // Listar todas as sessões de monitorias criadas por um monitor específico
    List<Sessao> findByMonitoria_Monitor_UsuarioId(Integer monitorId);

    // Buscar sessões por status (aguardando aprovação, deferida, recusada)
    List<Sessao> findByStatus(StatusSessao status);

    // Buscar sessões futuras de um aluno
    List<Sessao> findByAluno_UsuarioIdAndDataAfter(Integer alunoId, LocalDateTime data);

    // Buscar sessões futuras de um monitor
    List<Sessao> findByMonitoria_Monitor_UsuarioIdAndDataAfter(Integer monitorId, LocalDateTime data);

    List<Sessao> findByAluno_UsuarioIdAndStatus(Integer alunoId, StatusSessao status);

}
