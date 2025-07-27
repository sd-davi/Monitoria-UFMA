package com.example.smu.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.smu.model.Disciplina;
import com.example.smu.model.Dto.AlunoCursoDto;
import com.example.smu.model.Dto.MonitoriaCurso;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;


public interface  DisciplinaRepository extends JpaRepository<Disciplina,Integer> {
    
    Optional<Disciplina> findById(Integer id);
    Optional<Disciplina> findByNome(String nome);
    Optional<Disciplina> findByCodigo(String codigo);
    boolean existsByName(String nome);
    boolean existsById(Integer id);
    // sql

    // listar alunos na disciplina
    @Query("""
    SELECT com.example.smu.model.Dto.DisciplinaAluno(
    a.id,
    a.nome,
    d.id,
    d.nome
    )
    FROM Disciplina d
    JOIN d.alunos a
    WHERE d.id =: disciplinaid
    """) List<AlunoCursoDto> AlunosPorDisciplina (@Param("disciplinaid") Integer disciplinaid);

    // lista de monitorias disponiveis
    @Query("""
    SELECT com.example.smu.model.Dto.DisciplinaMonitoria(
    d.id,
    m.id,
    m.nome
    )
    FROM Disciplina d
    JOIN Monitoria m ON m.disciplina.id = d.id
    WHERE d.id =: disciplinaid
    """) List<MonitoriaCurso> monitoriaPorDisciplina(@Param("disciplinaid") Integer disciplinaid);

    // lista de alunos na monitoria

    
}
