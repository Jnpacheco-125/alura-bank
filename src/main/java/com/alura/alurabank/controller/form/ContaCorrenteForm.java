package com.alura.alurabank.controller.form;

import com.alura.alurabank.dominio.ContaCorrente;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContaCorrenteForm {
    @JsonProperty
    private String banco;
    @JsonProperty
    private String agencia;
    @JsonProperty
    private String numero;

    public ContaCorrente toContaCorrente(){
        return new ContaCorrente(banco, agencia, numero);
    }
}
