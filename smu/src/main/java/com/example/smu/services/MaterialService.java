package com.example.smu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.smu.model.Material;
import com.example.smu.model.Monitoria;
import com.example.smu.model.repository.MaterialRepository;
import com.example.smu.model.repository.MonitoriaRepository;
import com.example.smu.model.repository.UsuarioRepository;

@Service
public class MaterialService {
    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    MonitoriaRepository monitoriaRepository;
    @Autowired
    MaterialRepository materialRepository;

     // 1. Criar material para uma monitoria
    public Material criarMaterial(Integer monitoriaId, String titulo, String descricao, String link, String arquivo) {
        Monitoria monitoria = monitoriaRepository.findById(monitoriaId)
            .orElseThrow(() -> new RuntimeException("Monitoria não encontrada"));

        if (materialRepository.existsByTitulo(titulo)) {
            throw new RuntimeException("Já existe um material com esse título");
        }

        Material material = new Material();
        material.setTitulo(titulo);
        material.setDescricao(descricao);
        material.setLink(link);
        material.setArquivo(arquivo);
        material.setMonitoria(monitoria);

        return materialRepository.save(material);
    }

    // 2. Buscar material por ID
    public Material buscarMaterialPorId(Integer id) {
        return materialRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Material não encontrado"));
    }

    // 3. Buscar material por título
    public Material buscarPorTitulo(String titulo) {
        return materialRepository.findByTitulo(titulo)
            .orElseThrow(() -> new RuntimeException("Material com título não encontrado"));
    }

    // 4. Listar todos os materiais de uma monitoria
    public List<Material> listarMateriaisPorMonitoria(Integer monitoriaId) {
        return materialRepository.findAll().stream()
            .filter(m -> m.getMonitoria().getId().equals(monitoriaId))
            .toList();
    }

    // 5. Atualizar material
    public Material atualizarMaterial(Integer id, String novoTitulo, String novaDescricao, String novoLink, String novoArquivo) {
        Material material = buscarMaterialPorId(id);
        material.setTitulo(novoTitulo);
        material.setDescricao(novaDescricao);
        material.setLink(novoLink);
        material.setArquivo(novoArquivo);
        return materialRepository.save(material);
    }

    // 6. Deletar material
    public void deletarMaterial(Integer id) {
        if (!materialRepository.existsById(id)) {
            throw new RuntimeException("Material não encontrado");
        }
        materialRepository.deleteById(id);
    }
}
