package com.example.smu.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smu.model.Mensagem;
import com.example.smu.model.repository.MensagemRepository;
import com.example.smu.services.exceptions.MensagemRunTime;

import jakarta.transaction.Transactional;

@Service
public class MensagemService {
    @Autowired
    MensagemRepository repository;

    // Salvar
    @Transactional
    public Mensagem EnviarMensagem(Mensagem m){
        VerificarMensagem(m);
        return repository.save(m);
    }

    // buscar por id
    public Optional<Mensagem> BuscarPorID(Integer id){
        VerificarId(id);
        return repository.findById(id);
    }
    // deletar por id
    @Transactional
    public void deletarMensagem(Integer id) {
        VerificarId(id);
        repository.deleteById(id);
    }

    //chat
    public List<Mensagem> buscarMensagensPorSessao(Integer sessaoId) {
        return repository.findBySessao_IdOrderByHorarioAsc(sessaoId);
    }

    /**
     * Busca todas as mensagens de um autor específico em uma sessão.
     */
    public List<Mensagem> buscarMensagensPorAutorNaSessao(Integer sessaoId, Integer autorId) {
        return repository.findBySessao_IdAndAutor_IdOrderByHorarioAsc(sessaoId, autorId);
    }


    private void VerificarMensagem(Mensagem m){
        if (m == null){
            throw new MensagemRunTime("corpo da mensageem está nulo");
        }
        if(m.getConteudo() == null || m.getConteudo().trim().isEmpty()){
            throw new MensagemRunTime("corpo da mensageem está vazio");
        }
        if (m.getHorario() == null) {
            m.setHorario(LocalDateTime.now());
        }
    }

    private void VerificarId(Integer id){
        if (!repository.existsById(id)){
            throw new MensagemRunTime("Mensagem invalido");
        }
    }



}
