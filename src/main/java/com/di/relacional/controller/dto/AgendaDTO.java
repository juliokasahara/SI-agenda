package com.di.relacional.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendaDTO {

    private Long id;
    private String descricao;
    private Date data;
    private List<Long> servicoIds;
    private Long clienteId;
    private Long funcionarioId;
    private Long usuarioId;

}
