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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.smu.controller.Dto.AlunosPorDisciplinaDTO;
import com.example.smu.controller.Dto.UsuarioDto;
import com.example.smu.controller.Dto.UsuarioResponseDTO;
import com.example.smu.model.Curso;
import com.example.smu.model.Usuario;
import com.example.smu.services.CursoService;
import com.example.smu.services.UsuarioService;
import com.example.smu.services.exceptions.DisciplinaRunTime;
import com.example.smu.services.exceptions.MonitoriaRunTime;
import com.example.smu.services.exceptions.UsuarioRunTime;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService service;

    @Autowired
    CursoService  cursoService;

    // salvar
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody UsuarioDto usuarioRequest){

        try {
            Curso c =  cursoService.buscarPorId(usuarioRequest.getIdCurso());
       
            Usuario user = Usuario.builder()
                              .nome(usuarioRequest.getNome())
                              .email(usuarioRequest.getEmail())
                              .senha(usuarioRequest.getSenha())
                              .dataNascimento(usuarioRequest.getDataNascimento())
                              .matricula(usuarioRequest.getMatricula())
                              .tipo(usuarioRequest.getTipo())
                              .curso(c).build();
            Usuario salvo = service.salvar(user);
            return new ResponseEntity(salvo, HttpStatus.CREATED);
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.badRequest().body("Curso ou usuário não encontrado");    
        } catch (UsuarioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // logar
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioDto dto) {
        try {
            Usuario logado = service.login(
                dto.getEmail(),
                dto.getSenha(),
                dto.getTipo());
            return ResponseEntity.ok(logado);
        } catch (UsuarioRunTime e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            Usuario usuario = service.buscarPorId(id);
            AlunosPorDisciplinaDTO dto = new AlunosPorDisciplinaDTO(usuario);
            return ResponseEntity.ok(dto);
        } catch (UsuarioRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Listar todos os usuários
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Integer id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (UsuarioRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Inscrever aluno em uma disciplina
        @PostMapping("/{idUsuario}/disciplinas/{idDisciplina}/inscrever")
        public ResponseEntity<?> inscreverEmDisciplina(@PathVariable Integer idUsuario,
                                                    @PathVariable Integer idDisciplina) {
            try {
                service.inscreverAlunoEmDisciplina(idUsuario, idDisciplina);
                return ResponseEntity.ok("Aluno inscrito com sucesso na disciplina.");
            } catch (UsuarioRunTime | DisciplinaRunTime e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }


    // Inscrever-se em monitoria
    // Inscrever aluno em uma monitoria
        @PostMapping("/{idUsuario}/monitorias/{idMonitoria}/inscrever")
        public ResponseEntity<?> inscreverEmMonitoria(@PathVariable Integer idUsuario,
                                                    @PathVariable Integer idMonitoria) {
            try {
                service.inscreverAlunoEmMonitoria(idUsuario, idMonitoria);
                return ResponseEntity.ok("Aluno inscrito com sucesso na monitoria.");
            } catch (UsuarioRunTime | MonitoriaRunTime e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }

    // ---------- CONVERSÃO PARA DTO ----------
    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .dataNascimento(usuario.getDataNascimento())
                .matricula(usuario.getMatricula())
                .tipoUsuario(usuario.getTipo())
                .cursoId(usuario.getCurso().getId())
                .cursoNome(usuario.getCurso().getNome())
                .build();
    }
}
