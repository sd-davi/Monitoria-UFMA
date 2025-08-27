package com.example.smu.controller;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smu.controller.Dto.NotificacaoRequestDTO;
import com.example.smu.controller.Dto.NotificacaoResponseDTO;
import com.example.smu.model.Notificacao;
import com.example.smu.model.Usuario;
import com.example.smu.services.NotificacaoService;
import com.example.smu.services.UsuarioService;

import jakarta.persistence.EntityNotFoundException;




@RestController
@RequestMapping("/api/notificacao")
public class NotificacaoController {
    @Autowired
    private NotificacaoService service;

    @Autowired
    private UsuarioService usuarioService;

    //criar

    @PostMapping
    public ResponseEntity<?> criarNotificacao(@RequestBody NotificacaoRequestDTO dto) {
        try {
            Usuario usuarioDestino = usuarioService.buscarPorId(dto.getUsuarioDestinoId());

            Notificacao notificacao = Notificacao.builder()
                    .mensagem(dto.getMensagem())
                    .destinatario(usuarioDestino)
                    .horario(LocalDateTime.now())
                    .lida(false)
                    .build();

            Notificacao salva = service.RegistrarNotificacao(notificacao);
            return ResponseEntity.ok(toResponseDTO(salva));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Usuário destino não encontrado.");
        }
    }

    //deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable Integer id) {
        try {
            service.ExcluirNotificacao(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    //buscar
    @GetMapping("/{id}")
    public ResponseEntity<NotificacaoResponseDTO> buscarPorId(@PathVariable Integer id) {
        try {
            Notificacao notificacao = service.buscarPorId(id);
            return ResponseEntity.ok(toResponseDTO(notificacao));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<NotificacaoResponseDTO>> listarPorUsuario(@PathVariable Integer usuarioId) {
        List<NotificacaoResponseDTO> notificacoes = service.listarPorUsuario(usuarioId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(notificacoes);
    }

    //editar
    @PutMapping("/{id}/lida")
    public ResponseEntity<NotificacaoResponseDTO> marcarComoLida(@PathVariable Integer id) {
        try {
            Notificacao notificacao = service.marcarComoLida(id);
            return ResponseEntity.ok(toResponseDTO(notificacao));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    // Conversão para DTO
    private NotificacaoResponseDTO toResponseDTO(Notificacao notificacao) {
        return NotificacaoResponseDTO.builder()
                .notificacaoId(notificacao.getNotificacaoid())
                .mensagem(notificacao.getMensagem())
                .dataCriacao(notificacao.getHorario())
                .lida(false)
                .usuarioDestinoId(notificacao.getDestinatario().getId())
                .usuarioDestinoNome(notificacao.getDestinatario().getNome())
                .build();
    }
}
