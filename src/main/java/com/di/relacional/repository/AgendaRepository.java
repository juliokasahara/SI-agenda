package com.di.relacional.repository;

import com.di.relacional.model.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface AgendaRepository extends JpaRepository<Agenda,Long> {
    @Query(value = "SELECT * FROM AGENDA WHERE FUNCIONARIO_ID = :idFuncionario " +
            "AND DATA >= :data " +
            "AND DATA < :dataMais30Min",
            nativeQuery = true)
    List<Agenda> findAgendamento(@Param("idFuncionario") long idFuncionario,
                                 @Param("data") Date data,
                                 @Param("dataMais30Min") Date dataMais30Min);
}
