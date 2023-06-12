package com.alura.alurabank.controller.form;

import com.alura.alurabank.dominio.ContaCorrente;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


public class ContaCorrenteForm {
    @JsonProperty
    @NotBlank(message = "Banco é um campo obrigatorio e não pode estar em branco")
    private String banco;
    @JsonProperty
    @NotBlank(message = "Agencia é um campo obrigatorio e  não pode estar em branco")
    private String agencia;
    @JsonProperty
    @NotBlank(message = "Numero da conta é um campo obrigatorio e não pode estar em branco")
    private String numero;

    public ContaCorrente toContaCorrente(){
        return new ContaCorrente(banco, agencia, numero);
    }
}
