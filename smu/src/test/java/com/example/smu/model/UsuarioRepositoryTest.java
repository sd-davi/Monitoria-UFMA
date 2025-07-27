package com.example.smu.model;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.smu.model.repository.CursoRepository;
import com.example.smu.model.repository.UsuarioRepository;

@ExtendWith(SpringExtension.class) // permite acessar o codigo principal
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository ur;

    @Autowired
    CursoRepository cursoRepo;

    @Test
    public void deveSalvarUsuarioComCurso() {
        // cenário
        Curso curso = Curso.builder().nome("Engenharia").build();
        curso = cursoRepo.save(curso);

        Usuario novo = Usuario.builder()
                .nome("Davi")
                .email("davi22566@gmail.com")
                .senha("123")
                .matricula("123453327856467456611")
                .dataNascimento(LocalDateTime.of(2000, 1, 1, 0, 0))
                .tipo(TipoUsuario.ALUNO)
                .curso(curso)
                .build();

        // ação
        Usuario retorno = ur.save(novo);

        // verificação
        Assertions.assertNotNull(retorno);
        Assertions.assertEquals(novo.getNome(), retorno.getNome());
        Assertions.assertEquals(novo.getEmail(), retorno.getEmail());
        Assertions.assertEquals(novo.getSenha(), retorno.getSenha());
        Assertions.assertEquals(novo.getMatricula(), retorno.getMatricula());
        Assertions.assertEquals(novo.getTipo(), retorno.getTipo());
        Assertions.assertEquals(curso.getId(), retorno.getCurso().getId());

        // rollback
        ur.delete(retorno);
        cursoRepo.delete(curso);
    }

    @Test
    public void deveRemoverUsuario(){

    // cenário
    Curso curso = Curso.builder().nome("Engenharia").build();
    curso = cursoRepo.save(curso);

    Usuario novo =  Usuario.builder()
                .nome("Davi")
                .email("davi1834843@gmail.com")
                .senha("123")
                .matricula("123432343356")
                .dataNascimento(LocalDateTime.of(2000, 1, 1, 0, 0))
                .tipo(TipoUsuario.ALUNO)
                .curso(curso)
                .build();

    // ação
    Usuario salvo = ur.save(novo);
    Integer id = salvo.getId();
    ur.deleteById(id);

    // verificação
    Optional<Usuario> temp = ur.findById(id);
    Assertions.assertFalse(temp.isPresent());
    }
}
