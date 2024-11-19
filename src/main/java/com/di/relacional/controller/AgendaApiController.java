package com.di.relacional.controller;

import com.di.relacional.model.Agenda;
import com.di.relacional.serve.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/agenda")
public class AgendaApiController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping()
    public List<Agenda> buscarTodosAPI(){
        return agendaService.findAll();
    }

}
