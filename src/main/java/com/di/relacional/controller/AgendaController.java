package com.di.relacional.controller;

import com.di.relacional.controller.dto.AgendaDTO;
import com.di.relacional.controller.form.AgendaForm;
import com.di.relacional.model.Agenda;
import com.di.relacional.serve.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;
    @Autowired
    private ServicoService servicoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private FuncionarioService funcionarioService;
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ModelAndView buscarTodos(){
        ModelAndView modelAndView = new ModelAndView("agenda");
        modelAndView.addObject("agendas", agendaService.findAll());
        return modelAndView;
    }

    @GetMapping("/cadastrar")
    public ModelAndView cadastrarAgenda(){
        ModelAndView modelAndView = new ModelAndView("cadastro-agenda");
        modelAndView.addObject("agenda", new AgendaDTO());
        modelAndView.addObject("servicos", servicoService.findAll());
        modelAndView.addObject("funcionarios", funcionarioService.findAll());
        modelAndView.addObject("clientes", clienteService.findAll());
        modelAndView.addObject("usuarios", usuarioService.findAll());
        return modelAndView;
    }

    @PostMapping("/salvar")
    public ResponseEntity<String> salvarAgenda(@ModelAttribute AgendaForm agendaForm) throws ParseException {

        return agendaService.saveAndUpdate(agendaForm,null);

//        return new ModelAndView("redirect:/agenda");
    }

    @PostMapping("/salvar/{id}")
    public ResponseEntity<String> atualizarAgenda(@PathVariable(required = false) Long id,@ModelAttribute AgendaForm agendaForm) throws ParseException {

        return agendaService.saveAndUpdate(agendaForm,id);

//        return new ModelAndView("redirect:/agenda");
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editarAgenda(@PathVariable("id") Long id){

        ModelAndView modelAndView = new ModelAndView("cadastro-agenda");
        var agenda = agendaService.findById(id);

        if(agendaService.podeDeletar(agenda)){
            return new ModelAndView("redirect:/agenda");
        }

        List<Long> servicoIds = agenda.getAgendaServicos()
                .stream()
                .map(agendaServico -> agendaServico.getServico().getId()).collect(Collectors.toList());

        modelAndView.addObject("agenda", agenda);
        modelAndView.addObject("clientes", clienteService.findAll());
        modelAndView.addObject("usuarios", usuarioService.findAll());
        modelAndView.addObject("funcionarios", funcionarioService.findAll());
        modelAndView.addObject("servicos", servicoService.findAll());
        modelAndView.addObject("servicoIds", servicoIds);

        return modelAndView;
    }

    @GetMapping("/atualizar/{idAgenda}/servico/{idServico}")
    public ModelAndView atualizarAgenda(
            @PathVariable("idAgenda") Long idAgenda,
            @PathVariable("idServico") Long idServico
    ){
        agendaService.atualizarStatus(idServico,idAgenda);
        return buscarTodos();
    }
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<String> deletarAgenda(@PathVariable("id") Long id){

        String message = agendaService.deletar(id);
        return ResponseEntity.ok(message);
    }

}
