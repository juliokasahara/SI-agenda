package com.di.relacional.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgendaForm {

    private Long id;
    private String descricao;
    private String data;
    private List<String> servicoIds;
    private String cliente;
    private String usuario;
    private String funcionario;

}