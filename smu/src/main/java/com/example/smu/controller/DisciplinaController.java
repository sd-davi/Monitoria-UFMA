package com.example.smu.controller;

import java.util.List;
import java.util.stream.Collectors;

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

import com.example.smu.controller.Dto.DisciplinaDto;
import com.example.smu.controller.Dto.DisciplinaListDTO;
import com.example.smu.controller.Dto.DisciplinasComAlunosDTO;
import com.example.smu.model.Curso;
import com.example.smu.model.Disciplina;
import com.example.smu.services.CursoService;
import com.example.smu.services.DisciplinaService;
import com.example.smu.services.exceptions.DisciplinaRunTime;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/disciplina")
public class DisciplinaController {

    @Autowired
    private DisciplinaService service;

    @Autowired
    CursoService  cursoService;

    // Salvar disciplina
    @PostMapping("/salvar")
    public ResponseEntity<?> salvar(@RequestBody DisciplinaDto dto) {
        try {

            Curso c =  cursoService.buscarPorId(dto.getIdCurso());
            Disciplina disciplina = Disciplina.builder()
                .nome(dto.getNome())
                .codigo(dto.getCodigo())
                .curso(c)
                .build();

            Disciplina salva = service.SalvarDisciplina (disciplina);
            return new ResponseEntity<>(salva, HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Curso não encontrado");
        } catch (DisciplinaRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            Disciplina disciplina = service.buscarPorId(id);
            DisciplinasComAlunosDTO dto = new DisciplinasComAlunosDTO(disciplina);
            return ResponseEntity.ok(dto);
        } catch (DisciplinaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Listar todas
    @GetMapping
    public ResponseEntity<List<DisciplinaListDTO>> listarTodos() {
        List<Disciplina> d = service.listarTodas();

        List<DisciplinaListDTO> disciplinaDTO = d.stream().map(DisciplinaListDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(disciplinaDTO);
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

    @PutMapping("/{id}")
    public ResponseEntity<?> Atualizar(@PathVariable Integer id,
    @RequestBody Disciplina atualizada){

        try {
            Disciplina temp = service.Atualizar(id, atualizada);
            DisciplinaDto dto = new DisciplinaDto(temp);
            return ResponseEntity.ok(dto);
        } catch (DisciplinaRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
