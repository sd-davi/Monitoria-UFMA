package com.example.smu.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smu.model.Curso;

public interface  CursoRepository extends JpaRepository<Integer, Curso> {
    
}
