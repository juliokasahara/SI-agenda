package com.di.relacional.controller;

import com.di.relacional.model.Funcionario;
import com.di.relacional.serve.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/funcionario")
public class FuncionarioApiController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping()
    public List<Funcionario> buscarTodosAPI(){
        return funcionarioService.findAll();
    }

}
