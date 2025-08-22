package com.example.smu.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smu.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

    // Listar todas as avaliações de uma sessão
    List<Avaliacao> findBySessaoAgendada_SessaoId(Integer sessaoId);

    // Listar todas as avaliações feitas por um aluno específico
    List<Avaliacao> findByAluno_UsuarioId(Integer alunoId);

    // Calcular a média de estrelas de uma sessão (para uso em service)
    // Exemplo de query derivada para pegar só as estrelas
    List<Avaliacao> findBySessaoAgendada_SessaoIdAndEstrelasNotNull(Integer sessaoId);
}
