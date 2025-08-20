package com.example.smu.controller;

import java.util.List;

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

import com.example.smu.controller.Dto.CursoComAlunosDTO;
import com.example.smu.controller.Dto.CursoDto;
import com.example.smu.model.Curso;
import com.example.smu.model.Dto.AlunoCursoDto;
import com.example.smu.model.Dto.DisciplinaCursoDto;
import com.example.smu.model.Dto.MonitoriaCurso;
import com.example.smu.services.CursoService;
import com.example.smu.services.exceptions.CursoRunTime;

@RestController
@RequestMapping("api/curso")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    // Salvar curso
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody CursoDto dto) {
        try {
            Curso curso = Curso.builder()
                .nome(dto.getNome())
                .codigo(dto.getCodigo())
                .build();

            Curso salvo = cursoService.criarCurso(curso);
            return new ResponseEntity<>(salvo, HttpStatus.CREATED);
        } catch (CursoRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        Curso c =  cursoService.buscarPorId(id);
        CursoComAlunosDTO dto = new CursoComAlunosDTO(c);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/nome/{nome}")
    public Curso buscarPorNome(@PathVariable String nome) {
        return cursoService.buscarPorNome(nome);
    }

    @GetMapping
    public List<Curso> listarTodos() {
        return cursoService.listarTodos();
    }

    @PutMapping("/{id}")
    public Curso atualizarCurso(@PathVariable Integer id, @RequestBody Curso novoCurso) {
        return cursoService.atualizarCurso(id, novoCurso);
    }

    @DeleteMapping("/{id}")
    public void deletarCurso(@PathVariable Integer id) {
        cursoService.deletarCurso(id);
    }

    @GetMapping("/{id}/alunos")
    public List<AlunoCursoDto> listarAlunos(@PathVariable Integer id) {
        return cursoService.listarAlunos(id);
    }

    @GetMapping("/{id}/disciplinas")
    public List<DisciplinaCursoDto> listarDisciplinas(@PathVariable Integer id) {
        return cursoService.listarDisciplinas(id);
    }
    
    @GetMapping("/{id}/monitorias")
    public List<MonitoriaCurso> listarMonitorias(@PathVariable Integer id) {
        return cursoService.listarMonitorias(id);
    }
}

