package com.example.smu.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.smu.model.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material,Integer>{

    Optional<Material> findById(Integer id);
    boolean existsById (Integer id);
    void deleteById(Integer id);
    Optional<Material> findByTitulo(String titulo);
    boolean existsByTitulo(String titulo);
    List<Material> findByMonitoria(Integer mID);
}
