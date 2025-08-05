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

import com.example.smu.controller.Dto.DisciplinaDto;
import com.example.smu.model.Curso;
import com.example.smu.model.Disciplina;
import com.example.smu.services.DisciplinaService;
import com.example.smu.services.exceptions.DisciplinaRunTime;

@RestController
@RequestMapping("/api/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService service;

    // Salvar disciplina
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody DisciplinaDto dto) {
        try {
            Disciplina disciplina = Disciplina.builder()
                .nome(dto.getNome())
                .codigo(dto.getCodigo())
                .curso(Curso.builder().id(dto.getIdCurso()).build())
                .build();

            Disciplina salva = service.SalvarDisciplina (disciplina);
            return new ResponseEntity<>(salva, HttpStatus.CREATED);
        } catch (DisciplinaRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            Disciplina disciplina = service.buscarPorId(id);
            return ResponseEntity.ok(disciplina);
        } catch (DisciplinaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<Disciplina>> listarTodos() {
        return ResponseEntity.ok(service.listarTodas());
    }

    // Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (DisciplinaRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
