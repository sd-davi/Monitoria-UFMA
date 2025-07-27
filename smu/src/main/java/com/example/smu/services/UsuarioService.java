package com.example.smu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smu.model.Dto.DisciplinaAluno;
import com.example.smu.model.Dto.MonitoriaAlunoDto;
import com.example.smu.model.Dto.MonitoriaMonitorDto;
import com.example.smu.model.Curso;
import com.example.smu.model.TipoUsuario;
import com.example.smu.model.Usuario;
import com.example.smu.model.repository.UsuarioRepository;
import com.example.smu.services.exceptions.UsuarioRunTime;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    @Autowired
    UsuarioRepository usuarioRepository;

    // salvar
    @Transactional // so salva apos o final (para evitar dados incosistentes), se tiver problemas faz um rollback
    public Usuario salvar(Usuario user){
        VerificarUsuario(user);
        if (usuarioRepository.existsByEmail(user.getEmail())) {
            throw new UsuarioRunTime("Email já cadastrado.");
        }
        return usuarioRepository.save(user);
    }

    private void VerificarUsuario(Usuario user){

        if (user == null) {
        throw new UsuarioRunTime("Usuário nulo");
    }

    if (user.getNome() == null || user.getNome().trim().isEmpty()) {
        throw new UsuarioRunTime("Nome inválido");
    }

    if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
        throw new UsuarioRunTime("Email inválido");
    }

    if (user.getSenha() == null || user.getSenha().trim().isEmpty()) {
        throw new UsuarioRunTime("Senha inválida");
    }

    if (user.getMatricula() == null || user.getMatricula().trim().isEmpty()) {
        throw new UsuarioRunTime("Matrícula inválida");
    }

    if (user.getTipo() == null) {
        throw new UsuarioRunTime("Tipo de usuário não definido");
    }

    if (user.getCurso() == null) {
        throw new UsuarioRunTime("Curso não atribuído ao usuário");
    }
    }
    
    public Usuario login(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha)
                .orElseThrow(() -> new UsuarioRunTime("Credenciais inválidas"));
    }

    // consultas
    private void VerificarId(Integer id){
        if (!usuarioRepository.existsById(id)){
            throw new UsuarioRunTime("Usuario invalido");
        }
    }

    public Usuario obterUsuarioPorNome(String nome) {
        return usuarioRepository.obterUsuarioPorNome(nome);
    }
    // Buscar usuários por tipo (ALUNO, MONITOR)
    public List<Usuario> buscarUsuariosPorTipo(TipoUsuario tipo) {
        return usuarioRepository.findByTipo(tipo);
    }

    // Buscar monitores por curso
    public List<Usuario> buscarMonitoresPorCurso(Integer cursoId) {
        return usuarioRepository.findByTipoAndCursoId(TipoUsuario.MONITOR, cursoId);
    }

    // Buscar alunos por curso
    public List<Usuario> buscarAlunosPorCurso(Curso curso) {
        return usuarioRepository.findByTipoAndCursoId(TipoUsuario.ALUNO, curso.getId());
    }

    public List<DisciplinaAluno> DisciplinasPorAluno (Integer id){
        VerificarId(id);
        return usuarioRepository.DisciplinasPorAluno(id);
    }

    public List<MonitoriaAlunoDto> MonitoriasPorAluno(Integer id){
        VerificarId(id);
        return usuarioRepository.MonitoriasPorAluno(id);
    }

    public List<MonitoriaMonitorDto> MonitoriasPorMonitor(Integer id){
        VerificarId(id);
        return usuarioRepository.MonitoriasPorMonitor(id);
    }
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
     public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioRunTime("Usuário não encontrado"));
    }
}
