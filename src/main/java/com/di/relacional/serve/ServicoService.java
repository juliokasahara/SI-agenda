package com.di.relacional.serve;

import com.di.relacional.exception.ClienteNotFoundException;
import com.di.relacional.model.Cliente;
import com.di.relacional.model.Servico;
import com.di.relacional.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public List<Servico> findAll(){
        return servicoRepository.findAll();
    }

    public Servico findById(Long servicoId) {
            return servicoRepository.findById(servicoId).orElseThrow(() -> new ClienteNotFoundException("Servico com ID " + servicoId + " não encontrado"));
    }

    public List<Servico> findByIds(List<Long> servicoIds) {
        return servicoRepository.findAllByIdIn(servicoIds);
    }
}
