package com.di.relacional.serve;

import com.di.relacional.controller.form.AgendaForm;
import com.di.relacional.enummeration.ServicoStatusEnum;
import com.di.relacional.exception.ClienteNotFoundException;
import com.di.relacional.model.*;
import com.di.relacional.repository.AgendaRepository;
import com.di.relacional.repository.AgendaServicoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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
    private FuncionarioService funcionarioService;
    @Autowired
    private ServicoService servicoService;

    public List<Agenda> findAll(){
        return agendaRepository.findAll();
    }

    @Transactional
    public ResponseEntity<String> saveAndUpdate(AgendaForm agendaForm, Long id) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date data = formatter.parse(agendaForm.getData());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(data);
        calendar.add(Calendar.MINUTE, 30);
        Date dataMais30Min = calendar.getTime();

        List<Agenda> agendas = agendaRepository.findAgendamento(Long.parseLong(agendaForm.getFuncionario()),data,dataMais30Min);

        if (!agendas.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Existe agendamento no horario selecionado!<br> <button type=\"button\" class=\"btn btn-primary\" onclick=\"window.location.href='/agenda'\">Voltar</button>\n");
        }

        Agenda agenda = new Agenda();

        if (id != null) {
            agenda = excluirServicos(id,agendaForm);
        }

        Cliente cliente = clienteService.findById(Long.parseLong(agendaForm.getCliente()));
        Usuario usuario = usuarioService.findById(Long.parseLong(agendaForm.getUsuario()));
        Funcionario funcionario = funcionarioService.findById(Long.parseLong(agendaForm.getFuncionario()));

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
        agenda.setFuncionario(funcionario);

        for (Servico servico: servicos) {
            AgendaServico agendaServico = new AgendaServico(agenda,servico, ServicoStatusEnum.ABERTO.getStatus());
            agenda.getAgendaServicos().add(agendaServico);
        }
        agendaRepository.save(agenda);

        return ResponseEntity.ok("Agendamento realizado!<br> <button type=\"button\" class=\"btn btn-primary\" onclick=\"window.location.href='/agenda'\">Voltar</button>\n");
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

    public String deletar(Long id) {
        var agenda = agendaRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException("Agenda com ID " + id + " não encontrado"));
        Boolean deletar = podeDeletar(agenda);
        if (deletar) {
            return"Erro ao excluir, existem agendamentos concluídos";
        }
        agendaRepository.delete(agenda);
        return "Agenda deletada com sucesso";
    }

    public Boolean podeDeletar(Agenda agenda) {
        for (AgendaServico agendaServico: agenda.getAgendaServicos()) {
            if(agendaServico.getStatusEnum().equals(ServicoStatusEnum.CONCLUIDO.toString())){
                return true;
            }
        }
        return false;
    }

    public Agenda findById(Long id) {
        return agendaRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException("Agenda com ID " + id + " não encontrado"));
    }

    public void atualizarStatus(Long idServico,Long idAgenda) {
        AgendaServicoId idAgendaServicoId = new AgendaServicoId();
        idAgendaServicoId.setServicoId(idServico);
        idAgendaServicoId.setAgendaId(idAgenda);
        AgendaServico agendaServico = agendaServicoRepository.findById(idAgendaServicoId).orElseThrow(() -> new ClienteNotFoundException("Agenda com ID " + idAgendaServicoId + " não encontrado"));
        if(agendaServico.getStatusEnum().equals(ServicoStatusEnum.ABERTO.toString())){
            agendaServico.setStatusEnum(ServicoStatusEnum.CONCLUIDO.getStatus());
        }else{
            agendaServico.setStatusEnum(ServicoStatusEnum.ABERTO.getStatus());
        }
        agendaServicoRepository.save(agendaServico);
    }
}
