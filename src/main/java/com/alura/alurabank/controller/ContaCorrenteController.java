package com.alura.alurabank.controller;

import com.alura.alurabank.dominio.ContaCorrente;
import com.alura.alurabank.dominio.Correntista;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contas")
public class ContaCorrenteController {
    @GetMapping
    public String consultarSaldo(
            @RequestParam(name = "banco" ) String banco,
            @RequestParam(name = "agencia" )String agencia,
            @RequestParam(name = "numero" )String numero){
        return String.format("Banco: %s, Agencia: %s, Conta: %s. Saldo: R$1300,00",
                banco, agencia, numero);
    }
    @PostMapping
    public ResponseEntity<ContaCorrente> criarNovaConta(@RequestBody Correntista correntista){
        ContaCorrente conta = new ContaCorrente("111","2222","3333");
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }
    @DeleteMapping
    private String fecharConta(ContaCorrente conta){
        return "Conta fechada com sucesso";
    }

// http://localhost:8080/contas?banco=888&agencia=1111&numero=3333
}
