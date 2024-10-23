package com.di.relacional.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "agenda_servico")
public class AgendaServico {

    @Id
    @EmbeddedId
    private AgendaServicoId id = new AgendaServicoId();

    @JsonIgnore
    @ManyToOne
    @MapsId("agendaId")
    @JoinColumn(name = "agenda_id")
    private Agenda agenda;

    @ManyToOne
    @MapsId("servicoId")
    @JoinColumn(name = "servico_id")
    private Servico servico;

    private String statusEnum;

    public AgendaServico(Agenda agenda, Servico servico, String status) {
        this.id = new AgendaServicoId(agenda.getId(), servico.getId());
        this.agenda = agenda;
        this.servico = servico;
        this.statusEnum = status;
    }

}
