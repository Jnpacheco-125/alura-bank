package com.alura.alurabank.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;


@EqualsAndHashCode(
       // of = {"banco", "agencia", "numero"}), // Os atributos que eu quero
        exclude = {"correntista", "saldo"}) // tudo menos esses dois
public class ContaCorrente {
    @Getter
    @JsonProperty
    private String banco;
    @Getter
    @JsonProperty
    private String agencia;
    @Getter
    @JsonProperty
    private String numero;
    @JsonProperty
    private BigDecimal saldo;
    @JsonProperty
    private Correntista correntista;

    public ContaCorrente(String banco, String agencia, String numero, Correntista correntista) {
        this();
        this.banco = banco;
        this.agencia = agencia;
        this.numero = numero;
        this.correntista = correntista;

    }

    public ContaCorrente() {
        this.saldo = BigDecimal.ZERO;
    }

    public ContaCorrente(String banco, String agencia, String numero) {
        this.banco = banco;
        this.agencia = agencia;
        this.numero = numero;
    }

    public int obterNumeroConta(){
        return Integer.parseInt(numero);
    }
    public boolean identificadaPor(String banco, String agencia, String numero){
        return this.banco.equals(banco)
                && this.agencia.equals(agencia)
                && this.numero.equals(numero);

    }

    public BigDecimal lerSaldo() {
        return saldo;
    }
    public void executar(Operacao operacao, BigDecimal valor){
        saldo = operacao.executar(saldo, valor);
    }
}
