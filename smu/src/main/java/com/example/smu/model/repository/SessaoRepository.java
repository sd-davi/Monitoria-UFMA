package com.example.smu.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smu.model.Sessao;

import java.util.Optional;

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

}
