package com.example.smu.controller;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smu.controller.Dto.MensagemRequestDTO;
import com.example.smu.controller.Dto.MensagemResponseDTO;
import com.example.smu.model.Mensagem;
import com.example.smu.model.Sessao;
import com.example.smu.model.Usuario;
import com.example.smu.services.MensagemService;
import com.example.smu.services.SessaoService;
import com.example.smu.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;



@RestController
@RequestMapping("/api/mensagem")
public class MensagemController {
    
    @Autowired
    private MensagemService service;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private SessaoService sessaoService;

    //Enviar
    @PostMapping
    public ResponseEntity<?> EnviarMensagem(@RequestBody MensagemRequestDTO dto) {
        try {
            Usuario autor = usuarioService.buscarPorId(dto.getAutorId());
            Sessao sessao = sessaoService.buscarPorId(dto.getSessaoId());

            Mensagem mensagem = Mensagem.builder()
                    .conteudo(dto.getConteudo())
                    .autor(autor)
                    .sessao(sessao)
                    .build();

            Mensagem salva = service.EnviarMensagem(mensagem);
            return ResponseEntity.ok(toResponseDTO(salva));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Autor ou sessão não encontrada.");
        }
    }

    //deletar

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarMensagem(@PathVariable Integer id) {
        service.deletarMensagem(id);
        return ResponseEntity.noContent().build();
    }

    //buscar
    @GetMapping("/sessao/{sessaoId}")
    public ResponseEntity<List<MensagemResponseDTO>> listarPorSessao(@PathVariable Integer sessaoId) {
        List<MensagemResponseDTO> mensagens = service.buscarMensagensPorSessao(sessaoId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mensagens);
    }

    @GetMapping("/sessao/{sessaoId}/autor/{autorId}")
    public ResponseEntity<List<MensagemResponseDTO>> listarPorAutorNaSessao(@PathVariable Integer sessaoId,
                                                                           @PathVariable Integer autorId) {
        List<MensagemResponseDTO> mensagens = service.buscarMensagensPorAutorNaSessao(sessaoId, autorId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(mensagens);
    }
    //editar


    // Conversão para DTO
    private MensagemResponseDTO toResponseDTO(Mensagem mensagem) {
        return MensagemResponseDTO.builder()
                .mensagemId(mensagem.getMensagemId())
                .conteudo(mensagem.getConteudo())
                .dataHora(mensagem.getHorario())
                .autorId(mensagem.getAutor().getId())
                .autorNome(mensagem.getAutor().getNome())
                .sessaoId(mensagem.getSessao().getId())
                .build();
    }
}
