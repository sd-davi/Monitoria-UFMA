package com.example.smu.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.smu.model.Material;

public interface MaterialRepository extends JpaRepository<Material,Integer>{

    Optional<Material> findById(Integer id);
    boolean existsById (Integer id);
    void deleteById(Integer id);
    Optional<Material> findByTitulo(String titulo);
    boolean existsByTitulo(String titulo);
    List<Material> findByMonitoria_Id(Integer mID);
}
