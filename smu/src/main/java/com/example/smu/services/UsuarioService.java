package com.example.smu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smu.model.Curso;
import com.example.smu.model.Disciplina;
import com.example.smu.model.Dto.DisciplinaAluno;
import com.example.smu.model.Dto.MonitoriaAlunoDto;
import com.example.smu.model.Monitoria;
import com.example.smu.model.TipoUsuario;
import com.example.smu.model.Usuario;
import com.example.smu.model.repository.CursoRepository;
import com.example.smu.model.repository.DisciplinaRepository;
import com.example.smu.model.repository.MonitoriaRepository;
import com.example.smu.model.repository.UsuarioRepository;
import com.example.smu.services.exceptions.CursoRunTime;
import com.example.smu.services.exceptions.DisciplinaRunTime;
import com.example.smu.services.exceptions.MonitoriaRunTime;
import com.example.smu.services.exceptions.UsuarioRunTime;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {
    
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    MonitoriaRepository monitoriaRepository;
    @Autowired
    DisciplinaRepository disciplinaRepository;
    @Autowired
    CursoRepository cursoRepository;

    private static final String TOKEN_MONITOR = "MONITOR123";

    // salvar
    @Transactional // so salva apos o final (para evitar dados incosistentes), se tiver problemas faz um rollback
    public Usuario salvar(Usuario user, String tokenMonitor){
        Curso curso = cursoRepository.findById(user.getCurso().getId())
    .orElseThrow(() -> new CursoRunTime("Curso não encontrado"));

        VerificarUsuario(user);
        if (usuarioRepository.existsByEmail(user.getEmail())) {
            throw new UsuarioRunTime("Email já cadastrado.");
        }
        if (user.getTipo() == TipoUsuario.MONITOR) {
            if (!TOKEN_MONITOR.equals(tokenMonitor)) {
                throw new IllegalArgumentException("Token de monitor inválido.");
            }
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
    
    // adicionar monitoria
    
    public void inscreverAlunoEmMonitoria(Integer alunoId, Integer monitoriaId) {
        // Verifica se o aluno existe
        Usuario aluno = usuarioRepository.findById(alunoId)
            .orElseThrow(() -> new UsuarioRunTime("Aluno não encontrado"));

        // Verifica se é realmente um aluno
        if (!aluno.getTipo().equals(TipoUsuario.ALUNO)) {
            throw new UsuarioRunTime("Usuário não é um aluno");
        }

        // Verifica se a monitoria existe
        Monitoria monitoria = monitoriaRepository.findById(monitoriaId)
            .orElseThrow(() -> new MonitoriaRunTime("Monitoria não encontrada"));

        // verifica se aluno ja esta inscrito na monitoria
        if (aluno.getMonitorias().contains(monitoria)) {
            throw new UsuarioRunTime("Aluno já está inscrito nesta monitoria");
        }

        // Adiciona a monitoria ao aluno
        aluno.getMonitorias().add(monitoria);

        // Também adiciona o aluno à monitoria (para manter a bidirecionalidade correta)
        monitoria.getAlunos().add(aluno);

        usuarioRepository.save(aluno);
        monitoriaRepository.save(monitoria);
    }
    // inscrever em disciplina
    public void inscreverAlunoEmDisciplina(Integer alunoId, Integer disciplinaId) {
    // Verifica se o aluno existe
    Usuario aluno = usuarioRepository.findById(alunoId)
        .orElseThrow(() -> new UsuarioRunTime("Aluno não encontrado"));

    // Verifica se é realmente um aluno
    if (!aluno.getTipo().equals(TipoUsuario.ALUNO)) {
        throw new UsuarioRunTime("Usuário não é um aluno");
    }

    // Verifica se a disciplina existe
    Disciplina disciplina = disciplinaRepository.findById(disciplinaId)
        .orElseThrow(() -> new DisciplinaRunTime("Disciplina não encontrada"));

    // Verifica se o aluno já está inscrito
    if (aluno.getDisciplinas().contains(disciplina)) {
        throw new UsuarioRunTime("Aluno já está inscrito nesta disciplina");
    }

    // Inscreve
    aluno.getDisciplinas().add(disciplina);
    disciplina.getAlunos().add(aluno);

    usuarioRepository.save(aluno);
    disciplinaRepository.save(disciplina);
}

    
    // fazer login
    public Usuario login(String email, String senha, TipoUsuario tipo) {
        Usuario usuario = usuarioRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("E-mail ou senha inválidos"));

        if (!usuario.getSenha().equals(senha)) {
        throw new IllegalArgumentException("E-mail ou senha inválidos");
        }
        if (!usuario.getTipo().equals(tipo)) {
        throw new IllegalArgumentException("Tipo de usuário incorreto");
        }
        return usuario;
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

   /*  public List<MonitoriaMonitorDto> MonitoriasPorMonitor(Integer id){
        VerificarId(id);
        return usuarioRepository.MonitoriasPorMonitor(id);
    }*/
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }
    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioRunTime("Usuário não encontrado"));
    }

    @Transactional
    public void deletar(Integer id) {
        VerificarId(id);
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public Usuario atualizar(Usuario atualizado) {
        Usuario existente = buscarPorId(atualizado.getId());
        existente.setNome(atualizado.getNome());
        existente.setEmail(atualizado.getEmail());
        existente.setSenha(atualizado.getSenha());
        existente.setDataNascimento(atualizado.getDataNascimento());
        existente.setMatricula(atualizado.getMatricula());
        existente.setTipo(atualizado.getTipo());
        existente.setCurso(atualizado.getCurso());

        return usuarioRepository.save(existente);
    }

    public boolean existsByMatricula(String matricula) {
    return usuarioRepository.existsByMatricula(matricula);
    }

  public boolean existsByEmail(String email) {
    return usuarioRepository.existsByEmail(email);
  }

}
