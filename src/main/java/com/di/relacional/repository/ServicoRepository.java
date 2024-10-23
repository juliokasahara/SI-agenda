package com.di.relacional.repository;

import com.di.relacional.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicoRepository extends JpaRepository<Servico,Long> {
    List<Servico> findAllByIdIn(List<Long> servicoIds);
}
