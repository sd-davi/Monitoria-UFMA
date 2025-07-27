package com.example.smu.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.smu.model.Curso;
import com.example.smu.model.TipoUsuario;
import com.example.smu.model.Usuario;
import com.example.smu.model.Dto.AlunoCursoDto;
import com.example.smu.model.Dto.DisciplinaAluno;
import com.example.smu.model.Dto.MonitoriaAlunoDto;
import com.example.smu.model.Dto.MonitoriaMonitorDto;

public interface  UsuarioRepository extends JpaRepository<Usuario,Integer>{
    
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByEmailAndNome(String email, String nome);
    Optional<Usuario> findByEmailAndSenha(String email, String senha);
    Optional<Usuario> findByNomeContaining(String nome);
    boolean existsByEmail(String email);
    void deleteByNome(String nome);
    long countByNome(String nome);
    boolean existsByMatricula(String matricula);
    Optional<Usuario> findByMatricula(String matricula);
    List<Usuario> findByCurso(Curso curso);
    // Buscar todos os alunos
    List<Usuario> findByTipo(TipoUsuario tipo);

    // Buscar todos os monitores de um curso específico
    List<Usuario> findByTipoAndCursoId(TipoUsuario tipo, Integer cursoId);

    
    // consultas "SQL"

    // buscar usuario por nome
    @Query("select u from Usuario u where u.nome=:nomeUsuario")
    Usuario obterUsuarioPorNome(@Param("nomeUsuario") String nomeUsuario);

    

    // listar disciplinas do aluno
    @Query("""
    SELECT new com.example.smu.model.Dto.DisciplinaAluno(
    a.id,
    a.nome,
    d.id,
    d.nome
    )
    FROM Usuario a
    JOIN a.disciplinas d
    WHERE a.id =: alunoid
    """) List<DisciplinaAluno> DisciplinasPorAluno (@Param("alunoid") Integer alunoid);
    

    // listar monitorias do aluno
    @Query("""
    SELECT new com.example.smu.model.Dto.MonitoriaAlunoDto(
    a.id,
    a.nome,
    m.id,
    m.nome
    )
    FROM Usuario a
    JOIN a.monitorias m
    WHERE a.id =: alunoid
    """) List<MonitoriaAlunoDto> MonitoriasPorAluno (@Param("alunoid") Integer alunoid);

    
    // listar monitorias do monitor
    @Query("""
    SELECT new com.example.smu.model.Dto.MonitoriaMonitorDto(
    mo.id,
    mo.nome,
    m.id,
    m.nome
    )
    FROM Usuario mo
    JOIN Monitorias m ON m.monitor.id = mo.id
    WHERE mo.id =: monitorid
    """) List<MonitoriaMonitorDto> MonitoriasPorMonitor (@Param("monitorid") Integer monitorid);
    
}
