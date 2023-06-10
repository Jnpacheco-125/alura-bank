package com.alura.alurabank.controller;

import com.alura.alurabank.dominio.ContaCorrente;
import com.alura.alurabank.dominio.Correntista;
import com.alura.alurabank.dominio.MovimentacaoDeContas;
import com.alura.alurabank.repositorio.RepositorioContaCorrente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private RepositorioContaCorrente repositorioContaCorrente;
    @GetMapping
    public String consultarSaldo(
            @RequestParam(name = "banco" ) String banco,
            @RequestParam(name = "agencia" )String agencia,
            @RequestParam(name = "numero" )String numero){
        return String.format("Banco: %s, Agencia: %s, Conta: %s. Saldo: R$1300,00",
                banco, agencia, numero);
        // http://localhost:8080/contas?banco=888&agencia=1111&numero=3333
    }
    @PostMapping
    public ResponseEntity<ContaCorrente> criarNovaConta(@RequestBody Correntista correntista){
        String banco = "333";
        String agencia = "44444";
        String numero = Integer.toString(new Random().nextInt(Integer.MAX_VALUE));
        ContaCorrente conta = new ContaCorrente(banco, agencia, numero, correntista);
        repositorioContaCorrente.salvar(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }
    @DeleteMapping
    private String fecharConta(ContaCorrente conta){
        return "Conta fechada com sucesso";
    }

    @PutMapping
    public ResponseEntity<MovimentacaoDeContas> movimentacaoConta(
            @RequestBody MovimentacaoDeContas movimentacao){
       return ResponseEntity.ok(movimentacao);
    }

}
