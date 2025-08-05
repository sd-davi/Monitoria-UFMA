package com.example.smu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smu.model.Dto.MonitoriaAlunoDto;
import com.example.smu.model.Dto.MonitoriaMaterialDto;
import com.example.smu.model.Dto.MonitoriaSessaoDto;
import com.example.smu.model.Monitoria;
import com.example.smu.model.Usuario;
import com.example.smu.model.repository.CursoRepository;
import com.example.smu.model.repository.DisciplinaRepository;
import com.example.smu.model.repository.MonitoriaRepository;
import com.example.smu.model.repository.UsuarioRepository;
import com.example.smu.services.exceptions.MonitoriaRunTime;
import com.example.smu.services.exceptions.UsuarioRunTime;

@Service
public class MonitoriaService {

    @Autowired
    MonitoriaRepository monitoriaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    CursoRepository cursoRepository;
    @Autowired
    DisciplinaRepository disciplinaRepository;

    // cadastrar
    private void VerificarMonitoria(Monitoria m){

        if(m == null){
            throw new MonitoriaRunTime("Monitoria nula");
        }
        // curso disciplina monitor
        if(m.getCurso() == null){
            throw new MonitoriaRunTime("Monitoria não pertence a um curso");
        }
        if(m.getDisciplina() == null){
            throw new MonitoriaRunTime("Monitoria não pertence a uma disciplina");
        }
        if(m.getMonitor() == null){
            throw new MonitoriaRunTime("Monitoria não possui monitor");
        }
    }

    public Monitoria cadastrarMonitoria(Monitoria m){
        VerificarMonitoria(m);
        return monitoriaRepository.save(m);
    }
    // adicionar aluno a monitoria
    public void adicionarAlunoNaMonitoria(Integer monitoriaId, Integer alunoId) {
        Monitoria monitoria = monitoriaRepository.findById(monitoriaId)
                .orElseThrow(() -> new MonitoriaRunTime("Monitoria não encontrada"));

        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new UsuarioRunTime("Aluno não encontrado"));

        monitoria.getAlunos().add(aluno);
        monitoriaRepository.save(monitoria);
    }
    // consulta
    public Monitoria buscarPorId(Integer id) {
        return monitoriaRepository.findById(id)
                .orElseThrow(() -> new MonitoriaRunTime("Monitoria não encontrada"));
    }

    public void deletarPorId(Integer id) {
        if (!monitoriaRepository.existsById(id)) {
            throw new MonitoriaRunTime("Monitoria inválida");
        }
        monitoriaRepository.deleteById(id);
    }

    private void VerificarId(Integer id){
        if (! monitoriaRepository.existsById(id)){
            throw new MonitoriaRunTime("Monitoria não existe");
        }
    }

    public List<MonitoriaAlunoDto> AlunosPorMonitoria(Integer id){
        VerificarId(id);
        return monitoriaRepository.AlunosPorMonitoria(id);
    }

    public List<MonitoriaMaterialDto> MateriaisPorMonitoria(Integer id){
        VerificarId(id);
        return monitoriaRepository.MateriaisPorMonitoria(id);
    }

    public List<MonitoriaSessaoDto> SessoesPorMonitoria(Integer id){
        VerificarId(id);
        return monitoriaRepository.SessoesPorMonitoria(id);
    }

    public List<Monitoria> listarTodos() {
        return monitoriaRepository.findAll();
    }
    /* 
    public MonitoriaMonitorDto buscarMonitor(Integer monitoriaId) {
    return monitoriaRepository.buscarMonitor(monitoriaId)
        .orElseThrow(() -> new MonitoriaRunTime("Monitor não encontrado"));
}*/
}
