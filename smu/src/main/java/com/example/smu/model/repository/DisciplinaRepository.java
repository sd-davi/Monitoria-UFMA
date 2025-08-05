package com.example.smu.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.smu.model.Disciplina;
import com.example.smu.model.Dto.DisciplinaAluno;
import com.example.smu.model.Dto.DisciplinaMonitoria;


public interface  DisciplinaRepository extends JpaRepository<Disciplina,Integer> {
    
    Optional<Disciplina> findById(Integer id);
    Optional<Disciplina> findByNome(String nome);
    Optional<Disciplina> findByCodigo(String codigo);
    boolean existsByNome(String nome);
    boolean existsById(Integer id);
    // sql

    // listar alunos na disciplina
    @Query("""
    SELECT new com.example.smu.model.Dto.DisciplinaAluno(
    a.id,
    a.nome,
    d.id,
    d.nome
    )
    FROM Disciplina d
    JOIN d.alunos a
    WHERE d.id =: disciplinaid
    """) List<DisciplinaAluno> AlunosPorDisciplina (@Param("disciplinaid") Integer disciplinaid);

    // lista de monitorias disponiveis
    @Query("""
    SELECT new com.example.smu.model.Dto.DisciplinaMonitoria(
    d.id,
    m.id,
    m.nome
    )
    FROM Disciplina d
    JOIN Monitoria m ON m.disciplina.id = d.id
    WHERE d.id =: disciplinaid
    """) List<DisciplinaMonitoria> monitoriaPorDisciplina(@Param("disciplinaid") Integer disciplinaid);


    
}
