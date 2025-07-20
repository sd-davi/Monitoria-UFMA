package com.example.smu.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_usuario")
    Integer id;

    String nome;
    
    @Column(unique = true)
    String email;
    String senha;
    LocalDateTime dataNascimento;
    String matricula;

    @Enumerated(EnumType.STRING)
    TipoUsuario tipo;

    // curso
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;
    // monitoria 
    @ManyToMany
    @JoinTable(
        name= "usuario_monitoria",
        joinColumns= @JoinColumn(name="usuario_id"),
        inverseJoinColumns = @JoinColumn(name="monitoria_id")
    )
    Set<Monitoria> monitorias;
    // disciplina

    @ManyToMany
    @JoinTable(
        name= "usuario_disciplina",
        joinColumns = @JoinColumn(name="usuario_id"),
        inverseJoinColumns= @JoinColumn(name= "disciplina_id")
    )
    Set<Disciplina> disciplinas;





}
