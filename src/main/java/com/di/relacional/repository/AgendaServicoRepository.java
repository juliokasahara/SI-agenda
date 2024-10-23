package com.di.relacional.repository;

import com.di.relacional.model.AgendaServico;
import com.di.relacional.model.AgendaServicoId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgendaServicoRepository extends JpaRepository<AgendaServico, AgendaServicoId> {

    @Transactional
    @Modifying
    @Query("DELETE FROM AgendaServico a WHERE a.id.agendaId = :#{#id.agendaId} AND a.id.servicoId = :#{#id.servicoId}")
    void deleteById(@Param("id") AgendaServicoId id);

}
