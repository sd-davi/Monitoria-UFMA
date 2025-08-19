package com.example.smu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smu.controller.Dto.MonitoriaDto;
import com.example.smu.model.Curso;
import com.example.smu.model.Disciplina;
import com.example.smu.model.Monitoria;
import com.example.smu.model.Usuario;
import com.example.smu.services.CursoService;
import com.example.smu.services.DisciplinaService;
import com.example.smu.services.MonitoriaService;
import com.example.smu.services.UsuarioService;
import com.example.smu.services.exceptions.MonitoriaRunTime;
import com.example.smu.services.exceptions.UsuarioRunTime;

@RestController
@RequestMapping("/api/monitoria")
public class MonitoriaController {
    
    @Autowired
    MonitoriaService service;

    @Autowired
    CursoService cursoService;

    @Autowired
    DisciplinaService disciplinaService;

    @Autowired
    UsuarioService usuarioService;

    // salvar
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody MonitoriaDto monitoriaRequest) {
        try {
            Curso curso = cursoService.buscarPorId(monitoriaRequest.getIdCurso());
            Disciplina disc = disciplinaService.buscarPorId(monitoriaRequest.getIdDisciplina());
            Usuario monitor =  usuarioService.buscarPorId(monitoriaRequest.getIdMonitor());
            Monitoria monitoria = Monitoria.builder()
                    .nome(monitoriaRequest.getNome())
                    .curso(curso)
                    .disciplina(disc)
                    .monitor(monitor)
                    .build();

            Monitoria salva = service.cadastrarMonitoria(monitoria);
            return new ResponseEntity<>(salva, HttpStatus.CREATED);

        } catch (MonitoriaRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // buscar
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            Monitoria monitoria = service.buscarPorId(id);
            return ResponseEntity.ok(monitoria);
        } catch (MonitoriaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // GET: Listar todas as monitorias
    @GetMapping("/listar")
    public ResponseEntity<?> listarTudo() {
        List<Monitoria> monitorias = service.listarTodos();
        return ResponseEntity.ok(monitorias);
    }

    // DELETE: Deletar monitoria por ID
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            service.deletarPorId(id);
            return ResponseEntity.ok("Monitoria deletada com sucesso.");
        } catch (MonitoriaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

       // POST: Inscrever aluno em monitoria
    @PostMapping("/{monitoriaId}/inscrever/{alunoId}")
    public ResponseEntity<?> inscreverAluno(
        @PathVariable Integer monitoriaId,
        @PathVariable Integer alunoId) {
        try {
            service.adicionarAlunoNaMonitoria(alunoId, monitoriaId);
            return ResponseEntity.ok("Aluno inscrito com sucesso.");
        } catch (UsuarioRunTime | MonitoriaRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
 