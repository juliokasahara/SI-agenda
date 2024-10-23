package com.di.relacional.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String usuario;
    private String senha;
    private Date dtCadastro;
    @ManyToOne
    @JoinColumn(name = "tipo_acesso_id")
    private TipoAcesso tipoAcesso;

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public Date getDtCadastro() {
        return dtCadastro;
    }

    public TipoAcesso getTipoAcesso() {
        return tipoAcesso;
    }
}
