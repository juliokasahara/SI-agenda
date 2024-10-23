package com.di.relacional.serve;

import com.di.relacional.exception.ClienteNotFoundException;
import com.di.relacional.model.Funcionario;
import com.di.relacional.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public List<Funcionario> findAll(){
        return funcionarioRepository.findAll();
    }

    public Funcionario findById(Long funcionarioId) {
        return funcionarioRepository.findById(funcionarioId).orElseThrow(() -> new ClienteNotFoundException("Servico com ID " + funcionarioId + " não encontrado"));
    }

}
