package com.di.relacional.serve;

import com.di.relacional.exception.ClienteNotFoundException;
import com.di.relacional.model.Cliente;
import com.di.relacional.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Cliente findById(Long clienteId) {
        return clienteRepository.findById(clienteId).orElseThrow(() -> new ClienteNotFoundException("Cliente com ID " + clienteId + " não encontrado"));
    }
}
