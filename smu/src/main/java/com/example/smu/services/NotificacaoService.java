package com.example.smu.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import com.example.smu.model.Notificacao;
import com.example.smu.model.repository.NotificacaoRepository;

import com.example.smu.services.exceptions.NotificacaoRunTime;

@Service
public class NotificacaoService {
    @Autowired
    NotificacaoRepository repository;

    private void VerificarId(Integer id){
        if (!repository.existsById(id)){
            throw new NotificacaoRunTime("Notificação invalida");
        }
    }

    private void VerificarNotificacao(Notificacao n){
        if( n == null){
            throw new NotificacaoRunTime("corpo da notificação é nulo");
        }
        if(n.getMensagem() == null || n.getMensagem().trim().isEmpty()){
            throw new NotificacaoRunTime("corpo da mensagem está vazio");
        }
    }

    public Notificacao RegistrarNotificacao(Notificacao n){
        VerificarNotificacao(n);
        return repository.save(n);
    }

    public Notificacao buscarPorId(Integer id) {
        return repository.findById(id).
        orElseThrow(() -> new NotificacaoRunTime("Notificação não encontrada com ID " + id));     
    }

    public Notificacao marcarComoLida(Integer id) {
        Notificacao notificacao = buscarPorId(id);
        notificacao.setLida(true);
        return repository.save(notificacao);
    }

    public List<Notificacao> listarPorUsuario(Integer usuarioId) {
        return repository.findByUsuarioDestino_UsuarioId(usuarioId);
    }

    
    public void ExcluirNotificacao(Integer id){
        VerificarId(id);
        repository.deleteById(id);
    }


}