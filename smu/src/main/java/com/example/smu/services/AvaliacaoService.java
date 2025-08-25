package com.example.smu.services;
import java.util.List;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.smu.model.Avaliacao;
import com.example.smu.model.repository.AvaliacaoRepository;
import com.example.smu.services.exceptions.AvaliacaoRunTime;

@Service
public class AvaliacaoService {
    @Autowired
    AvaliacaoRepository repository;

    @Transactional
    public Avaliacao RegistarAvaliacao(Avaliacao a){
        if (a == null){
            throw new AvaliacaoRunTime("Avaliação nula");
        }
        return repository.save(a);
    }

    private void VerificarId(Integer id){
        if( ! repository.existsById(id)){
            throw new AvaliacaoRunTime("Avaliação não existe");
        }
    }
    @Transactional
    public void DeletarAvaliacao(Integer id){
        VerificarId(id);
        repository.deleteById(id);
    }

    public Avaliacao buscarPorId(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new AvaliacaoRunTime("Avaliação não encontrada"));
    }

    public List<Avaliacao> listarPorSessao(Integer sessaoId) {
        return repository.findBySessao_Id(sessaoId);
    }

    public List<Avaliacao> listarPorAluno(Integer alunoId) {
        return repository.findByAluno_Id(alunoId);
    }

    public double calcularMediaEstrelasPorSessao(Integer sessaoId) {
        List<Avaliacao> avaliacoes = repository.findBySessao_IdAndEstrelasNotNull(sessaoId);

        OptionalDouble media = avaliacoes.stream()
                .mapToInt(Avaliacao::getEstrelas)
                .average();

        return media.orElse(0.0);
    }



}
