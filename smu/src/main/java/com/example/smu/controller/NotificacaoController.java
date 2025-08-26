package com.example.smu.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.smu.services.NotificacaoService;




@RestController
@RequestMapping("/api/notificacao")
public class NotificacaoController {
    @Autowired
    private NotificacaoService service;

    //salvar

    //deletar

    //buscar

    //editar
}
