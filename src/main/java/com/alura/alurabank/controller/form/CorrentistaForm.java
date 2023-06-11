package com.alura.alurabank.controller.form;

import com.alura.alurabank.dominio.Correntista;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CorrentistaForm {
    @JsonProperty
    private String cpf;
    @JsonProperty
    private String nome;

    public Correntista toCorrentista() {
        return new Correntista(cpf, nome);
    }
}
