package com.di.relacional.serve;

import com.di.relacional.client.AddressClient;
import com.di.relacional.client.response.AddressResponse;
import com.di.relacional.controller.form.FuncionarioForm;
import com.di.relacional.exception.ClienteNotFoundException;
import com.di.relacional.model.Endereco;
import com.di.relacional.model.Funcionario;
import com.di.relacional.repository.EnderecoRepository;
import com.di.relacional.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private AddressClient addressClient;

    public List<Funcionario> findAll(){
        return funcionarioRepository.findAll();
    }

    public Funcionario findById(Long funcionarioId) {
        return funcionarioRepository.findById(funcionarioId).orElseThrow(() -> new ClienteNotFoundException("Servico com ID " + funcionarioId + " não encontrado"));
    }

    public void saveAndUpdate(FuncionarioForm funcionarioForm) {
        AddressResponse addressResponse =  addressClient.findCep(funcionarioForm.getCep()).getBody();
        Endereco endereco = new Endereco();
        endereco.setCep(funcionarioForm.getCep());
        endereco.setUf(addressResponse.getUf());
        endereco.setCidade(addressResponse.getLocalidade());
        endereco.setEstado(addressResponse.getEstado());
        endereco.setLogradouro(addressResponse.getLogradouro());
        endereco.setNumero(funcionarioForm.getNumero());
        endereco.setComplemento(funcionarioForm.getComplemento());
        endereco = enderecoRepository.save(endereco);

        Funcionario funcionario = new Funcionario();
        funcionario.setNome(funcionarioForm.getNome());
        funcionario.setTelefone(funcionarioForm.getTelefone());
        funcionario.setSalario(funcionarioForm.getSalario());
        funcionario.setEmail(funcionarioForm.getEmail());
        funcionario.setEndereco(endereco);

        funcionarioRepository.save(funcionario);
    }
}
