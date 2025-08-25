package com.example.smu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smu.model.Disciplina;
import com.example.smu.model.Dto.DisciplinaAluno;
import com.example.smu.model.Dto.DisciplinaMonitoria;
import com.example.smu.model.Usuario;
import com.example.smu.model.repository.DisciplinaRepository;
import com.example.smu.model.repository.UsuarioRepository;
import com.example.smu.services.exceptions.DisciplinaRunTime;
import com.example.smu.services.exceptions.UsuarioRunTime;

import jakarta.transaction.Transactional;

@Service
public class DisciplinaService {

    @Autowired
    DisciplinaRepository disciplinaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;
    //salvar

    public Disciplina SalvarDisciplina (Disciplina d){
        VerificarDisciplina(d);
        return disciplinaRepository.save(d);
    }

    private void VerificarDisciplina (Disciplina d){
        if(d == null){
            throw new DisciplinaRunTime("disciplina nula");
        }
        if (d.getNome() == null || d.getNome().trim().isEmpty()){
            throw new DisciplinaRunTime("Nome inválido");
        }
        if (d.getCodigo() == null || d.getCodigo().trim().isEmpty()){
            throw new DisciplinaRunTime("Código inválido");
        }
        if (d.getCurso() == null){
            throw new DisciplinaRunTime("disciplina não está vinculada a nenhum curso");
        }
    }

    // consultas
    // Buscar disciplina por ID
    public Disciplina buscarPorId(Integer id) {
        return disciplinaRepository.findById(id)
                .orElseThrow(() -> new DisciplinaRunTime("Disciplina não encontrada"));
    }

    // Buscar disciplina por nome
    public Disciplina buscarPorNome(String nome) {
        return disciplinaRepository.findByNome(nome)
                .orElseThrow(() -> new DisciplinaRunTime("Disciplina não encontrada"));
    }

    // Buscar disciplina por código
    public Disciplina buscarPorCodigo(String codigo) {
        return disciplinaRepository.findByCodigo(codigo)
                .orElseThrow(() -> new DisciplinaRunTime("Disciplina não encontrada"));
    }

    private void VerificarId(Integer id){
        if (!disciplinaRepository.existsById(id)){
            throw new DisciplinaRunTime("Disciplina invalida");
        }
    }

    public List<DisciplinaAluno> AlunosPorDisciplina(Integer id){
        VerificarId(id);
        return disciplinaRepository.AlunosPorDisciplina(id);
    }

    public List<DisciplinaMonitoria> monitoriaPorDisciplina(Integer id){
        VerificarId(id);
        return disciplinaRepository.monitoriaPorDisciplina(id);
    }

    // cadastrar e excluir aluno da disciplina
     public void adicionarAlunoNaDisciplina(Integer disciplinaId, Integer alunoId) {
        Disciplina disciplina = buscarPorId(disciplinaId);
        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new UsuarioRunTime("Aluno não encontrado"));

        disciplina.getAlunos().add(aluno);
        aluno.getDisciplinas().add(disciplina);

        disciplinaRepository.save(disciplina);
        usuarioRepository.save(aluno);
    }

    public void removerAlunoDaDisciplina(Integer disciplinaId, Integer alunoId) {
        Disciplina disciplina = buscarPorId(disciplinaId);
        Usuario aluno = usuarioRepository.findById(alunoId)
                .orElseThrow(() -> new UsuarioRunTime("Aluno não encontrado"));

        disciplina.getAlunos().remove(aluno);
        aluno.getDisciplinas().remove(disciplina);

        disciplinaRepository.save(disciplina);
        usuarioRepository.save(aluno);
    }

    public List<Disciplina> listarTodas() {
        return disciplinaRepository.findAll();
    }

    public void deletar(Integer id) {
        disciplinaRepository.deleteById(id);
    }


    @Transactional
    public Disciplina Atualizar(Integer id, Disciplina d){

        Disciplina existente = disciplinaRepository.findById(id)
        .orElseThrow(() -> new DisciplinaRunTime("Disciplina não encontrada"));

        existente.setNome(d.getNome());
        existente.setCodigo(d.getCodigo());

        return disciplinaRepository.save(existente);
    }

}
