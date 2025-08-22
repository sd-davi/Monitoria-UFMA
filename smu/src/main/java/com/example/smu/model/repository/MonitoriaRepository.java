package com.example.smu.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.smu.model.Dto.MonitoriaAlunoDto;
import com.example.smu.model.Dto.MonitoriaMaterialDto;
import com.example.smu.model.Dto.MonitoriaSessaoDto;
import com.example.smu.model.Monitoria;

public interface MonitoriaRepository extends JpaRepository<Monitoria,Integer> {
    
    Optional<Monitoria> findById(Integer id);
    boolean existsById (Integer id);
    void deleteById(Integer id);

    //listas
    List<Monitoria> findByCodigoDisciplina(String codigoDisciplina);
    List<Monitoria> findByDisciplinaContainingIgnoreCase(String disciplina);
    List<Monitoria> findByMonitor_UsuarioId(Long monitorId);
    List<Monitoria> findByCurso_CursoId(Long cursoId);
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
    WHERE m.id = :monitoriaid
    """) List<MonitoriaAlunoDto> AlunosPorMonitoria (@Param("monitoriaid") Integer monitoriaid);



    // lista de materiais na monitoria  MonitoriaMaterialDto
    @Query("""
    SELECT new com.example.smu.model.Dto.MonitoriaMaterialDto(
    mt.id,
    mt.titulo,
    m.id,
    m.nome
    )
    FROM Monitoria m
    JOIN m.materiais mt
    WHERE m.id = :monitoriaid
    """) List<MonitoriaMaterialDto> MateriaisPorMonitoria(@Param("monitoriaid") Integer monitoriaid);

    // listar as sessões MonitoriaSessaoDto
    @Query("""
    SELECT new com.example.smu.model.Dto.MonitoriaSessaoDto(
    s.id,
    m.id,
    m.nome
    )
    FROM Monitoria m
    JOIN m.sessoes s
    WHERE m.id = :monitoriaid
    """) List<MonitoriaSessaoDto> SessoesPorMonitoria(@Param("monitoriaid") Integer monitoriaid);



    @Query("SELECT m FROM Monitoria m WHERE " +
            "(:disciplina IS NOT NULL AND LOWER(m.disciplina) LIKE LOWER(CONCAT('%', :disciplina, '%')) " +
            "OR :monitor IS NOT NULL AND LOWER(m.monitor.nome) LIKE LOWER(CONCAT('%', :monitor, '%')))")
    List<Monitoria> buscarPorDisciplinaEporMonitor(
            @Param("disciplina") String disciplina,
            @Param("monitor") String monitor);



}
