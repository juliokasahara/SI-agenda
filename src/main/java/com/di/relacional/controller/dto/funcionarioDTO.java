package com.di.relacional.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class funcionarioDTO {

    private String nome;
    private String telefone;
    private BigDecimal salario;
    private String email;
    private String cep;
    private String numero;
    private String complemento;

}
