package com.di.relacional.serve;

import com.di.relacional.controller.form.AgendaForm;
import com.di.relacional.enummeration.ServicoStatusEnum;
import com.di.relacional.exception.ClienteNotFoundException;
import com.di.relacional.model.*;
import com.di.relacional.repository.AgendaRepository;
import com.di.relacional.repository.AgendaServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private AgendaServicoRepository agendaServicoRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ServicoService servicoService;

    public List<Agenda> findAll(){
        return agendaRepository.findAll();
    }

    @Transactional
    public void saveAndUpdate(AgendaForm agendaForm,Long id) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

        Agenda agenda = new Agenda();
        if (id != null) {
            agenda = excluirServicos(id,agendaForm);
        }

        Cliente cliente = clienteService.findById(Long.parseLong(agendaForm.getCliente()));
        Usuario usuario = usuarioService.findById(Long.parseLong(agendaForm.getUsuario()));

        List<Servico> servicos = new ArrayList<>();
        if(Objects.nonNull(agendaForm.getServicoIds())) {
            List<Long> servicoIds = agendaForm.getServicoIds().stream()
                    .map(Long::parseLong)
                    .collect(Collectors.toList());
            servicos = servicoService.findByIds(servicoIds);
        }

        agenda.setAgendaServicos(new ArrayList<>());
        agenda.setDescricao(agendaForm.getDescricao());
        agenda.setData(formatter.parse(agendaForm.getData()));
        agenda.setCliente(cliente);
        agenda.setUsuario(usuario);

        for (Servico servico: servicos) {
            AgendaServico agendaServico = new AgendaServico(agenda,servico, ServicoStatusEnum.ABERTO.getStatus());
            agenda.getAgendaServicos().add(agendaServico);
        }
        agendaRepository.save(agenda);
    }
    private Agenda excluirServicos(Long id,AgendaForm agendaForm) {
        Agenda agenda = findById(id);

        agenda.getAgendaServicos().forEach(agendaServico -> {
            if(agendaForm.getServicoIds() == null){
                agendaServicoRepository.deleteById(agendaServico.getId());
            }else if(!agendaForm.getServicoIds().contains(agendaServico.getServico().getId().toString())){
                agendaServicoRepository.deleteById(agendaServico.getId());
            }
        });
        return agenda;
    }

    public void deletar(Long id) {
        var agenda = agendaRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException("Agenda com ID " + id + " não encontrado"));
        agendaRepository.delete(agenda);
    }

    public Agenda findById(Long id) {
        return agendaRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException("Agenda com ID " + id + " não encontrado"));
    }
}
