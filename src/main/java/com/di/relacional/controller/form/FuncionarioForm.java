package com.di.relacional.controller.form;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioForm {

    private String nome;
    private String telefone;
    private BigDecimal salario;
    private String email;
    private String cep;
    private String numero;
    private String complemento;

}
