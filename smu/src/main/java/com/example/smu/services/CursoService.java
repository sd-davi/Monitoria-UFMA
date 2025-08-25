package com.example.smu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smu.model.Curso;
import com.example.smu.model.Dto.AlunoCursoDto;
import com.example.smu.model.Dto.DisciplinaCursoDto;
import com.example.smu.model.Dto.MonitoriaCurso;
import com.example.smu.model.repository.CursoRepository;
import com.example.smu.services.exceptions.CursoRunTime;
import com.example.smu.services.exceptions.MonitoriaRunTime;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    // criar curso
    public Curso criarCurso(Curso curso) {
        VerificarCurso(curso);
        if (cursoRepository.existsByNome(curso.getNome())) {
            throw new CursoRunTime("Curso já existe com o nome: " + curso.getNome());
        }
        return cursoRepository.save(curso);
    }
    private void VerificarCurso(Curso c){
        if(c == null){
            throw new CursoRunTime("Curso nulo");
        }
        if (c.getNome() == null || c.getNome().trim().isEmpty()){
            throw new CursoRunTime("Curso não tem nome");
        }
        if(c.getCodigo() == null || c.getCodigo().trim().isEmpty()){
            throw new CursoRunTime("Curso não tem codigo");
        }
    }
    //Buscar curso por ID
    public Curso buscarPorId(Integer id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new CursoRunTime("Curso não encontrado com ID: " + id));
    }

    //Buscar curso por nome
    public Curso buscarPorNome(String nome) {
        return cursoRepository.findBynome(nome)
                .orElseThrow(() -> new CursoRunTime("Curso não encontrado com nome: " + nome));
    }

    // Listar todos os cursos
    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    // Atualizar curso
    public Curso atualizarCurso(Integer id, Curso novoCurso) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new CursoRunTime("Curso não encontrado com ID: " + id));

        cursoExistente.setNome(novoCurso.getNome());
        return cursoRepository.save(cursoExistente);
    }

    // Deletar curso
    public void deletarCurso(Integer id) {
        if (!cursoRepository.existsById(id)) {
            throw new CursoRunTime("Curso não existe com ID: " + id);
        }
        cursoRepository.deleteById(id);
    }

     private void VerificarId(Integer id){
        if (! cursoRepository.existsById(id)){
            throw new MonitoriaRunTime("curso não existe");
        }
    }

    // Listar alunos do curso
    public List<AlunoCursoDto> listarAlunos(Integer cursoId) {
        VerificarId(cursoId);
        return cursoRepository.AlunosPorCurso(cursoId);
    }

    // Listar disciplinas do curso
    public List<DisciplinaCursoDto> listarDisciplinas(Integer cursoId) {
        VerificarId(cursoId);
        return cursoRepository.DisciplinasPorCurso(cursoId);
    }

    // Listar monitorias do curso
    
    public List<MonitoriaCurso> listarMonitorias(Integer cursoId) {
        VerificarId(cursoId);
        return cursoRepository.monitoriaPorCurso(cursoId);
    }
}

