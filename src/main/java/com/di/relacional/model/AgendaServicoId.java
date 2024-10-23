package com.di.relacional.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendaServicoId implements Serializable {

    private Long agendaId;
    private Long servicoId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgendaServicoId that = (AgendaServicoId) o;
        return Objects.equals(agendaId, that.agendaId) &&
                Objects.equals(servicoId, that.servicoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agendaId, servicoId);
    }

}
