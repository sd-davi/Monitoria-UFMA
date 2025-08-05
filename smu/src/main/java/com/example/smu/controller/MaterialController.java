package com.example.smu.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smu.controller.Dto.MaterialDto;
import com.example.smu.model.Material;
import com.example.smu.model.Monitoria;
import com.example.smu.services.MaterialService;
import com.example.smu.services.exceptions.MaterialRunTime;


@RestController
@RequestMapping("/api/material")
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody MaterialDto dto) {

        Integer monitoriaId = null;
        if (dto.getIdMonitoria() != null){
            monitoriaId = dto.getIdMonitoria();
        }
        Material material =  Material.builder()
                                     .titulo(dto.getTitulo())
                                     .descricao(dto.getDescricao())
                                     .link(dto.getLink())
                                     .arquivo(dto.getArquivo())
                                     .monitoria((Monitoria.builder().id(monitoriaId)).build())
                                     .build();
        try{
            Material salvo = materialService.criarMaterial(material);
            return new ResponseEntity(salvo, HttpStatus.CREATED);
        }catch(MaterialRunTime e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Buscar material por ID
    @GetMapping("/{id}")
    public Material buscarPorId(@PathVariable Integer id) {
        return materialService.buscarMaterialPorId(id);
    }

    // 3. Buscar material por título
    @GetMapping("/titulo/{titulo}")
    public Material buscarPorTitulo(@PathVariable String titulo) {
        return materialService.buscarPorTitulo(titulo);
    }

    // 4. Listar materiais por monitoria
    @GetMapping("/monitoria/{monitoriaId}")
    public List<Material> listarPorMonitoria(@PathVariable Integer monitoriaId) {
        return materialService.listarMateriaisPorMonitoria(monitoriaId);
    }

    // 5. Atualizar material
    @PutMapping("/{id}")
    public Material atualizar(@PathVariable Integer id, @RequestBody MaterialDto dto) {
        return materialService.atualizarMaterial(
            id,
            dto.getTitulo(),
            dto.getDescricao(),
            dto.getLink(),
            dto.getArquivo()
        );
    }

    // 6. Deletar material
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        materialService.deletarMaterial(id);
    }
}
