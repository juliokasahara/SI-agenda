package com.di.relacional.serve;

import com.di.relacional.repository.AgendaServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaServicoService {

    @Autowired
    private AgendaServicoRepository agendaServicoRepository;


}
