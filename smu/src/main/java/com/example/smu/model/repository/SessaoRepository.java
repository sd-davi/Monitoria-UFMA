package com.example.smu.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.smu.model.Sessao;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface  SessaoRepository extends JpaRepository<Sessao,Integer> {
    
}
