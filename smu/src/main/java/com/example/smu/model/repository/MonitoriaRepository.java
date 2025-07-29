package com.example.smu.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smu.model.Monitoria;
import com.example.smu.model.Usuario;
import com.example.smu.model.Dto.MonitoriaAlunoDto;
import com.example.smu.model.Dto.MonitoriaCurso;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.smu.model.Dto.MonitoriaMaterialDto;
import com.example.smu.model.Dto.MonitoriaMonitorDto;
import com.example.smu.model.Dto.MonitoriaSessaoDto;

public interface MonitoriaRepository extends JpaRepository<Monitoria,Integer> {
    
    Optional<Monitoria> findById(Integer id);
    boolean existsById (Integer id);
    void deleteById(Integer id);

    // sql

    // lista de alunos na monitoria
    @Query("""
    SELECT new com.example.smu.model.Dto.MonitoriaAlunoDto(
    a.id,
    a.nome,
    m.id,
    m.nome
    )
    FROM Monitoria m
    JOIN m.alunos a
    WHERE m.id =: monitoriaid
    """) List<MonitoriaAlunoDto> AlunosPorMonitoria (@Param("monitoriaid") Integer monitoriaid);



    // lista de materiais na monitoria  MonitoriaMaterialDto
    @Query("""
    SELECT new com.example.smu.model.Dto.MonitoriaMaterialDto(
    mt.id,
    mt.nome,
    m.id,
    m.nome
    )
    FROM Monitoria m
    JOIN Material mt ON mt.monitoria.id = m.id
    WHERE m.id =: monitoriaid
    """) List<MonitoriaMaterialDto> MateriaisPorMonitoria(@Param("monitoriaid") Integer monitoriaid);

    // listar as sessões MonitoriaSessaoDto
    @Query("""
    SELECT new com.example.smu.model.Dto.MonitoriaSessaoDto(
    s.id,
    m.id,
    m.nome
    )
    FROM Monitoria m
    JOIN Sessao s ON s.monitoria.id = m.id
    WHERE m.id =: monitoriaid
    """) List<MonitoriaSessaoDto> SessoesPorMonitoria(@Param("monitoriaid") Integer monitoriaid);


    // encontrar monitor MonitoriaMonitorDto.java

    @Query("""
    SELECT new com.example.smu.model.Dto.MonitoriaMonitorDto(
    m.id,
    monitor.id,
    monitor.nome
    )
    FROM Monitoria m
    JOIN m.monitor monitor
    WHERE m.id = :monitoriaid
""")
Optional<MonitoriaMonitorDto> buscarMonitor(@Param("monitoriaid") Integer monitoriaid);


}
