package com.example.smu.model;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "sessao")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Sessao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_sessao")
    private Integer id;
    
    String link;
    LocalDateTime horario;

    @ManyToOne
    @JoinColumn(name= "monitoria_id")
    Monitoria monitoria;

    // aluno
    @ManyToMany(mappedBy="sessoes")
    Set <Usuario> alunos;
}
