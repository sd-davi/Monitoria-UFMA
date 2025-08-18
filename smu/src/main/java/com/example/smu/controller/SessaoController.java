package com.example.smu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smu.controller.Dto.SessaoDto;
import com.example.smu.model.Monitoria;
import com.example.smu.model.Sessao;
import com.example.smu.services.SessaoService;
import com.example.smu.services.exceptions.SessaoRunTime;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {
    
    @Autowired
    private SessaoService sessaoService;

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody SessaoDto dto) {

        Integer monitoriaId = null;
        if (dto.getIdMonitoria() != null){
            monitoriaId = dto.getIdMonitoria();
        }

        Sessao sessao = Sessao.builder()
                            .horario(dto.getHorario())
                            .monitoria(Monitoria.builder().id(monitoriaId).build())
                            .build();
        try {
            Sessao salva = sessaoService.criarSessao(sessao);
            return new ResponseEntity(salva, HttpStatus.CREATED);
        } catch (SessaoRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            sessaoService.deletarPorId(id);
            return ResponseEntity.ok("Sessão deletada com sucesso.");
        } catch (SessaoRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            Sessao sessao = sessaoService.buscarPorId(id);
            return ResponseEntity.ok(sessao);
        } catch (SessaoRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity atualizar(@PathVariable Integer id, @RequestBody SessaoDto dto) {
        try {
            Sessao atualizado = sessaoService.atualizar(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
