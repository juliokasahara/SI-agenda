package com.di.relacional;

import com.di.relacional.client.AddressClient;
import com.di.relacional.enummeration.ServicoStatusEnum;
import com.di.relacional.model.*;
import com.di.relacional.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Component
public class DefaultValueDB {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ServicoRepository servicoRepository;
    @Autowired
    private AgendaRepository agendaRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private TipoAcessoRepository tipoAcessoRepository;
    @Autowired
    private AgendaServicoRepository agendaServicoRepository;

    @Bean
    public CommandLineRunner commandLineRunner(UsuarioRepository usuarioRepository,
                                               ClienteRepository clienteRepository,
                                               ServicoRepository servicoRepository,
                                               AgendaRepository agendaRepository,
                                               EnderecoRepository enderecoRepository,
                                               TipoAcessoRepository tipoAcessoRepository,
                                               AgendaServicoRepository agendaServicoRepository){
        return args -> {

            List<Usuario> usuarioList = usuarioRepository.findAll();

            if(usuarioList.isEmpty()){

                TipoAcesso adm = new TipoAcesso(null,"ADM");
                TipoAcesso usr = new TipoAcesso(null,"USER");
                usr = tipoAcessoRepository.save(adm);
                adm = tipoAcessoRepository.save(usr);

                Usuario ana = new Usuario(null,"Ana","ana@test.com", new Date(), adm);
                Usuario carlos = new Usuario(null,"Carlos","carlos@test.com", new Date(), usr);
                Usuario roger = new Usuario( null,"Roger","roger@test.com", new Date(), usr);
                ana = usuarioRepository.save(ana);
                carlos = usuarioRepository.save(carlos);
                roger = usuarioRepository.save(roger);

                Endereco enderecoCarla = new Endereco(null,"Rua 1","SP","SP","14800-200","Araraquara","332",null);
                Endereco enderecoJao = new Endereco(null,"Rua 1","SP","SP","14800-200","Araraquara","332",null);
                Endereco enderecoAndre = new Endereco(null,"Rua 1","SP","SP","14800-200","Araraquara","332",null);
                enderecoCarla = enderecoRepository.save(enderecoCarla);
                enderecoAndre = enderecoRepository.save(enderecoAndre);
                enderecoJao = enderecoRepository.save(enderecoJao);

                Servico servicoCorte = new Servico(null,"Corte",new BigDecimal(30.00),null);
                Servico servicoPintar = new Servico(null,"Pintar",new BigDecimal(80.00),null);
                Servico servicoLuzes = new Servico(null,"Luzes",new BigDecimal(100.00),null);
                Servico servicoBarba = new Servico(null,"Barba",new BigDecimal(15.00),null);
                servicoCorte = servicoRepository.save(servicoCorte);
                servicoPintar = servicoRepository.save(servicoPintar);
                servicoRepository.save(servicoLuzes);
                servicoRepository.save(servicoBarba);

                Cliente cliente = new Cliente(null,"Cliente Carla","99999999","carla@teste.com",enderecoCarla,null);
                Cliente cliente2 = new Cliente(null,"Cliente João","99999999","joao@teste.com",enderecoJao,null);
                cliente = clienteRepository.save(cliente);
                clienteRepository.save(cliente2);

                Funcionario funcionarioAndre = new Funcionario(null,"Funcionario Andre","99999999","joao@teste.com", new BigDecimal(1300.00),enderecoAndre);
                funcionarioAndre = funcionarioRepository.save(funcionarioAndre);
                Agenda agendaCarla = new Agenda(null,"Cortar Cabelo + Pintar Cabelo", new Date(),null,cliente,ana,funcionarioAndre);
                agendaCarla = agendaRepository.save(agendaCarla);

                AgendaServico agendaServico = new AgendaServico(agendaCarla, servicoCorte, ServicoStatusEnum.ABERTO.getStatus());
                AgendaServico pintarServico = new AgendaServico(agendaCarla, servicoPintar, ServicoStatusEnum.ABERTO.getStatus());
                agendaServicoRepository.save(agendaServico);
                agendaServicoRepository.save(pintarServico);

                System.out.println("Usuario criado com sucesso!");
            }
        };
    }
}
