package com.example.smu.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.smu.model.Material;
import com.example.smu.model.Monitoria;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MaterialRepository extends JpaRepository<Material,Integer>{

    Optional<Material> findById(Integer id);
    boolean existsById (Integer id);
    void deleteById(Integer id);
    Optional<Material> findByTitulo(String titulo);
    boolean existsByTitulo(String titulo);

}
