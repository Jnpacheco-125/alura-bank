package com.alura.alurabank.controller;

import com.alura.alurabank.config.JMapperBean;
import com.alura.alurabank.controller.form.ContaCorrenteForm;
import com.alura.alurabank.controller.form.CorrentistaForm;
import com.alura.alurabank.dominio.ContaCorrente;
import com.alura.alurabank.dominio.Correntista;
import com.alura.alurabank.dominio.MovimentacaoDeContas;
import com.alura.alurabank.repositorio.RepositorioContaCorrente;
import com.googlecode.jmapper.JMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/contas")
public class ContaCorrenteController {

    @Autowired
    private RepositorioContaCorrente repositorioContaCorrente;
//    @Autowired
//    private JMapper<ContaCorrente, ContaCorrenteForm> contaCorrenteMapper;
    @GetMapping
    public String consultarSaldo(
            @RequestParam(name = "banco" ) String banco,
            @RequestParam(name = "agencia" )String agencia,
            @RequestParam(name = "numero" )String numero){
        ContaCorrente contaCorrente = repositorioContaCorrente.buscar(banco, agencia, numero).orElse(new ContaCorrente());
        return String.format("Banco: %s, Agencia: %s, Conta: %s. Saldo: %s",
                banco, agencia, numero, contaCorrente.lerSaldo());
        // http://localhost:8080/contas?banco=888&agencia=1111&numero=3333
    }
    @PostMapping
    public ResponseEntity<ContaCorrente> criarNovaConta(@RequestBody CorrentistaForm correntistaForm){
        Correntista correntista = correntistaForm.toCorrentista();
        String banco = "333";
        String agencia = "44444";
        String numero = Integer.toString(new Random().nextInt(Integer.MAX_VALUE));
        ContaCorrente conta = new ContaCorrente(banco, agencia, numero, correntista);
        repositorioContaCorrente.salvar(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }
    @DeleteMapping
    private String fecharConta(@RequestBody ContaCorrenteForm contaCorrenteForm){
        ContaCorrente contaCorrente = contaCorrenteForm.toContaCorrente();
        repositorioContaCorrente.fechar(contaCorrente);
        return "Conta fechada com sucesso";
    }

    @PutMapping
    public ResponseEntity<String> movimentacaoConta(
            @RequestBody MovimentacaoDeContas movimentacao){
        Optional<ContaCorrente> opContaCorrente =
                repositorioContaCorrente.buscar(movimentacao.getBanco(),
                movimentacao.getAgencia(),
                movimentacao.getNumero());
        if(opContaCorrente.isEmpty()){
            return ResponseEntity.badRequest().body("Conta corrente não existe");
        }
        else{
            ContaCorrente contaCorrente = opContaCorrente.get();
            movimentacao.executarEm(contaCorrente);
            repositorioContaCorrente.salvar(contaCorrente);
            return ResponseEntity.ok("Movimentação realizada com sucesso");
        }
     }
}
