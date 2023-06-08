package com.alura.alurabank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContaCorrenteController {
    @GetMapping("/contas")
    public String consultarSaldo(
            @RequestParam(name = "banco" ) String banco,
            @RequestParam(name = "agencia" )String agencia,
            @RequestParam(name = "numero" )String numero){
        return String.format("Banco: %s, Agencia: %s, Conta: %s. Saldo: R$1300,00",
                banco, agencia, numero);
    }
// http://localhost:8080/contas?banco=888&agencia=1111&numero=3333
}
