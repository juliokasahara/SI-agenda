package com.di.relacional.serve;

import com.di.relacional.exception.ClienteNotFoundException;
import com.di.relacional.model.Usuario;
import com.di.relacional.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long usuarioId) {
        return usuarioRepository.findById(usuarioId).orElseThrow(() -> new ClienteNotFoundException("Usuario com ID " + usuarioId + " não encontrado"));
    }
}
