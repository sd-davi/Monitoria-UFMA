package com.example.smu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "material")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id_usuario")
    Integer id;
    
    String titulo;
    String descricao;
    String link;
    String arquivo;

    @ManyToOne
    @JoinColumn(name="monitoria_id")
    Monitoria monitoria;

}
