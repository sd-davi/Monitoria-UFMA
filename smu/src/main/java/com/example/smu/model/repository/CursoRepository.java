package com.example.smu.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.smu.model.Curso;
import com.example.smu.model.Dto.AlunoCursoDto;
import com.example.smu.model.Dto.DisciplinaCursoDto;
import com.example.smu.model.Dto.MonitoriaCurso;


public interface  CursoRepository extends JpaRepository<Curso,Integer> {
    
    Optional<Curso> findBynome (String nome);
    Optional<Curso> findById (Integer id);
    boolean existsByNome(String nome);
    boolean existsById(Integer id);
    // sql

    // listar alunos n0 curso
    @Query("""
    SELECT new com.example.smu.model.Dto.AlunoCursoDto(
    c.id,
    a.id,
    a.nome
    )
    FROM Curso c
    JOIN Usuario a ON a.curso.id = c.id
    WHERE c.id =: cursoid
    """) List<AlunoCursoDto> AlunosPorCurso (@Param("cursoid") Integer cursoid);

    // listar disciplinas no curso
    @Query("""
    SELECT new com.example.smu.model.Dto.DisciplinaCursoDto(
    c.id,
    d.id,
    d.nome
    )
    FROM Curso c
    JOIN Disciplina d ON d.curso.id = c.id
    WHERE c.id =: cursoid
    """) List<DisciplinaCursoDto> DisciplinasPorCurso(@Param("cursoid") Integer cursoid);

    // lista de monitorias disponiveis
    @Query("""
    SELECT new com.example.smu.model.Dto.MonitoriaCurso(
    c.id,
    m.id,
    m.nome
    )
    FROM Curso c
    JOIN Monitoria m ON m.curso.id = c.id
    WHERE c.id =: cursoid
    """) List<MonitoriaCurso> monitoriaPorCurso(@Param("cursoid") Integer cursoid);

}