package com.example.smu.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smu.controller.Dto.AlunosPorMonitoriaDTO;
import com.example.smu.controller.Dto.MonitoriaDto;
import com.example.smu.controller.Dto.MonitoriaListaDTO;
import com.example.smu.controller.Dto.MonitoriaResponseDto;
import com.example.smu.controller.Dto.MonitoriasComAluno;
import com.example.smu.model.Curso;
import com.example.smu.model.Disciplina;
import com.example.smu.model.Monitoria;
import com.example.smu.model.Usuario;
import com.example.smu.model.Dto.MonitoriaAlunoDto;
import com.example.smu.services.CursoService;
import com.example.smu.services.DisciplinaService;
import com.example.smu.services.MonitoriaService;
import com.example.smu.services.UsuarioService;
import com.example.smu.services.exceptions.MonitoriaRunTime;
import com.example.smu.services.exceptions.UsuarioRunTime;


@RestController
@RequestMapping("/api/monitoria")
@CrossOrigin("*")
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
                    .descricao(monitoriaRequest.getDescricao())
                    .diasDaSemana(monitoriaRequest.getDiasDaSemana())
                    .horario(monitoriaRequest.getHorario())
                    .link(monitoriaRequest.getLink())
                    .build();

            Monitoria salva = service.cadastrarMonitoria(monitoria);
            MonitoriaResponseDto dto = toResponseDto(salva);
            return new ResponseEntity<>(dto, HttpStatus.CREATED);

        } catch (MonitoriaRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<?> Atualizar(@PathVariable Integer id, 
    @RequestBody Monitoria atualizada) {
        try {
            Monitoria temp = service.atualizarMonitoria(id, atualizada);
            MonitoriaListaDTO dto = new MonitoriaListaDTO(temp);
            return ResponseEntity.ok(dto);
        } catch (MonitoriaRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        
    }

    // buscar
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
        try {
            Monitoria monitoria = service.buscarPorId(id);
            MonitoriasComAluno dto = new MonitoriasComAluno(monitoria);
            return ResponseEntity.ok(dto);
        } catch (MonitoriaRunTime e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    // buscar por monitor
    @GetMapping("/monitor/{idMonitor}")
    public ResponseEntity<List<MonitoriaResponseDto>> buscarMonitoriasDoMonitor(@PathVariable Integer idMonitor) {
    
    // 1. Busca as entidades (Monitoria) no banco de dados usando o Service
    List<Monitoria> monitorias = service.buscarPorMonitor(idMonitor);

    // 2. Transforma a lista de Entidades em uma lista de DTOs
    // Lembra daquele seu método private toResponseDto()? Vamos usá-lo aqui!
    List<MonitoriaResponseDto> dtos = monitorias.stream()
            .map(this::toResponseDto)
            .collect(Collectors.toList());

    // 3. Devolve para o front-end (Status 200 OK)
    return ResponseEntity.ok(dtos);
}
    // GET: Listar todas as monitorias
    @GetMapping
    public ResponseEntity<List<MonitoriaListaDTO>> listarMonitorias() {
        List<Monitoria> monitorias = service.listarTodos();
        List<MonitoriaListaDTO> listDTO = monitorias.stream().map(MonitoriaListaDTO::new).collect(Collectors.toList());
        return ResponseEntity.ok(listDTO);
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
            service.adicionarAlunoNaMonitoria(monitoriaId,alunoId);
            return ResponseEntity.ok("Aluno inscrito com sucesso.");
        } catch (UsuarioRunTime | MonitoriaRunTime e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // conversão
    private MonitoriaResponseDto toResponseDto(Monitoria monitoria) {
    return MonitoriaResponseDto.builder()
            .id(monitoria.getId())
            .nome(monitoria.getNome())
            .cursoNome(monitoria.getCurso().getNome())
            .disciplinaNome(monitoria.getDisciplina().getNome())
            .monitorNome(monitoria.getMonitor().getNome())
            .build();   
    }

    @GetMapping("/{id}/alunos")
    public List<MonitoriaAlunoDto> listarMonitorias(@PathVariable Integer id){
        return service.AlunosPorMonitoria(id);
    }

    @DeleteMapping("/{idMonitoria}/aluno/{idAluno}")
    public ResponseEntity<?> sairDaMonitoria(
            @PathVariable Integer idMonitoria, 
            @PathVariable Integer idAluno) {
        try {
            // Chama o método que criamos no MonitoriaService
            service.removerAlunoDaMonitoria(idMonitoria, idAluno);
            
            // Retorna status 200 OK para o JavaScript saber que deu certo
            return ResponseEntity.ok("Inscrição cancelada com sucesso.");
        } catch (Exception e) {
            // Se der erro (ex: aluno não encontrado), retorna um 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
 