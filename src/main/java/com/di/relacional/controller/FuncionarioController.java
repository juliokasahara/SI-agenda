package com.di.relacional.controller;

import com.di.relacional.controller.dto.AgendaDTO;
import com.di.relacional.controller.dto.funcionarioDTO;
import com.di.relacional.controller.form.AgendaForm;
import com.di.relacional.controller.form.FuncionarioForm;
import com.di.relacional.model.Agenda;
import com.di.relacional.serve.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funcionario")
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    @GetMapping
    public ModelAndView buscarTodos(){
        ModelAndView modelAndView = new ModelAndView("funcionario");
        modelAndView.addObject("funcionarios", funcionarioService.findAll());
        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrarAgenda(){
        ModelAndView modelAndView = new ModelAndView("cadastro-funcionario");
        modelAndView.addObject("funcionario", new funcionarioDTO());
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ModelAndView salvarAgenda(@ModelAttribute FuncionarioForm funcionarioForm) throws ParseException {

        funcionarioService.saveAndUpdate(funcionarioForm);

        return new ModelAndView("redirect:/agenda");
    }

}
