package com.example.smu.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smu.controller.Dto.AvaliacaoRequestDTO;
import com.example.smu.controller.Dto.AvaliacaoResponseDTO;
import com.example.smu.model.Avaliacao;
import com.example.smu.model.Sessao;
import com.example.smu.model.Usuario;
import com.example.smu.services.AvaliacaoService;
import com.example.smu.services.SessaoService;
import com.example.smu.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api/avaliacao")
@CrossOrigin("*")
public class AvaliacaoController {
    
    @Autowired
    private AvaliacaoService service;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SessaoService sessaoService;

    //salvar
    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody AvaliacaoRequestDTO dto) {
        try {
            Sessao sessao = sessaoService.buscarPorId(dto.getAlunoId());
            Usuario aluno = usuarioService.buscarPorId(dto.getAlunoId());

            Avaliacao avaliacao = Avaliacao.builder()
                    .estrelas(dto.getEstrelas())
                    .comentario(dto.getComentario())
                    .sessao(sessao)
                    .aluno(aluno)
                    .build();

            Avaliacao salva = service.RegistarAvaliacao(avaliacao);
            return ResponseEntity.ok(toResponseDTO(salva));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Sessão ou aluno não encontrado.");
        }
    }

    //deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        try {
            service.DeletarAvaliacao(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    //buscar
    @GetMapping("/{id}")
    public ResponseEntity<AvaliacaoResponseDTO> buscarPorId(@PathVariable Integer id) {
        try {
            Avaliacao avaliacao = service.buscarPorId(id);
            return ResponseEntity.ok(toResponseDTO(avaliacao));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    //editar

    // listagens

    @GetMapping("/sessao/{sessaoId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorSessao(@PathVariable Integer sessaoId) {
        List<AvaliacaoResponseDTO> avaliacoes = service.listarPorSessao(sessaoId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/aluno/{alunoId}")
    public ResponseEntity<List<AvaliacaoResponseDTO>> listarPorAluno(@PathVariable Integer alunoId) {
        List<AvaliacaoResponseDTO> avaliacoes = service.listarPorAluno(alunoId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(avaliacoes);
    }

    @GetMapping("/media/sessao/{sessaoId}")
    public ResponseEntity<Double> calcularMediaPorSessao(@PathVariable Integer sessaoId) {
        double media = service.calcularMediaEstrelasPorSessao(sessaoId);
        return ResponseEntity.ok(media);
    }
    // conversão

    private AvaliacaoResponseDTO toResponseDTO(Avaliacao avaliacao) {
        return AvaliacaoResponseDTO.builder()
                .avaliacaoId(avaliacao.getAvaliacaoId())
                .estrelas(avaliacao.getEstrelas())
                .comentario(avaliacao.getComentario())
                .sessaoId(avaliacao.getSessao().getId())
                .alunoId(avaliacao.getAluno().getId())
                .alunoNome(avaliacao.getAluno().getNome())
                .build();
    }
    
}
