package com.example.smu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smu.controller.Dto.SessaoDto;
import com.example.smu.model.Monitoria;
import com.example.smu.model.Sessao;
import com.example.smu.model.Usuario;
import com.example.smu.model.repository.MonitoriaRepository;
import com.example.smu.model.repository.SessaoRepository;
import com.example.smu.model.repository.UsuarioRepository;
import com.example.smu.services.exceptions.SessaoRunTime;
import com.example.smu.services.exceptions.UsuarioRunTime;

import jakarta.transaction.Transactional;

@Service
public class SessaoService {

    @Autowired
    SessaoRepository sessaoRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    MonitoriaRepository monitoriaRepository;

    // salvar

    @Transactional
    public Sessao criarSessao(Sessao sessao){
        return sessaoRepository.save(sessao);
    }

    @Transactional
    public void adicionarAlunoNaSessao(Integer alunoId, Integer sessaoId) {
        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new UsuarioRunTime("Aluno não encontrado"));

        Sessao sessao = sessaoRepository.findById(sessaoId)
                .orElseThrow(() -> new SessaoRunTime("Sessão não encontrada"));

        // Verifica se já está inscrito
        if (sessao.getAlunos().contains(aluno)) {
            throw new UsuarioRunTime("Aluno já está inscrito na sessão");
        }

        sessao.getAlunos().add(aluno);
        sessaoRepository.save(sessao);
    }

    private void VerificarId(Integer id){
        if (! usuarioRepository.existsById(id)){
            throw new UsuarioRunTime("Usuario invalido");
        }

    }

    public List<Sessao> findSessoesByAlunoId (Integer id){
        VerificarId(id);
        return sessaoRepository.findSessoesByAlunoId(id);
    }

    public void deletarPorId(Integer id) {
        if (!sessaoRepository.existsById(id)) {
            throw new SessaoRunTime("sessao inválida");
        }
        sessaoRepository.deleteById(id);
    }

    public Sessao buscarPorId(Integer id) {
        return sessaoRepository.findById(id)
                .orElseThrow(() -> new SessaoRunTime("Sessao não encontrada"));
    }

    public Sessao atualizar(Integer id, SessaoDto dto) {
    Sessao sessao = sessaoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Sessão não encontrada com o ID: " + id));

    // Atualiza os campos com os dados do DTO
    sessao.setLink(dto.getLink());
    sessao.setHorario(dto.getHorario());

    if (dto.getIdMonitoria() != null) {
        Monitoria monitoria = Monitoria.builder()
            .id(dto.getIdMonitoria())
            .build();
        sessao.setMonitoria(monitoria);
    }

    return sessaoRepository.save(sessao);
}

}
