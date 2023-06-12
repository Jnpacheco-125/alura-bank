package com.alura.alurabank.controller;

import com.alura.alurabank.config.JMapperBean;
import com.alura.alurabank.controller.form.ContaCorrenteForm;
import com.alura.alurabank.controller.form.CorrentistaForm;
import com.alura.alurabank.dominio.ContaCorrente;
import com.alura.alurabank.dominio.Correntista;
import com.alura.alurabank.dominio.MovimentacaoDeContas;
import com.alura.alurabank.repositorio.RepositorioContaCorrente;
import com.googlecode.jmapper.JMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contas")
public class ContaCorrenteController {

    @Autowired
    private RepositorioContaCorrente repositorioContaCorrente;
//    @Autowired
//    private JMapper<ContaCorrente, ContaCorrenteForm> contaCorrenteMapper;
    @Autowired
    private Validator validator;
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
    public ResponseEntity criarNovaConta(@RequestBody CorrentistaForm correntistaForm){
        Map<Path, String> violacoesToMap = validar(correntistaForm);

        if(!violacoesToMap.isEmpty()) return ResponseEntity.badRequest().body(violacoesToMap);
        Correntista correntista = correntistaForm.toCorrentista();
        String banco = "333";
        String agencia = "44444";
        String numero = Integer.toString(new Random().nextInt(Integer.MAX_VALUE));
        ContaCorrente conta = new ContaCorrente(banco, agencia, numero, correntista);
        repositorioContaCorrente.salvar(conta);
        return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    private  <T> Map<Path, String> validar(T form) {
        Set<ConstraintViolation<T>> violacoes =
                validator.validate(form);
        Map<Path, String> violacoesToMap = violacoes
                .stream()
                .collect(Collectors.toMap(
                violacao -> violacao.getPropertyPath(), violacao -> violacao.getMessage()));
        return violacoesToMap;
    }

    @DeleteMapping
    private ResponseEntity  fecharConta(@RequestBody ContaCorrenteForm contaCorrenteForm){
        Map<Path, String> violacoesToMap = validar(contaCorrenteForm);
        if(!violacoesToMap.isEmpty()) return ResponseEntity.badRequest().body(violacoesToMap);
        ContaCorrente contaCorrente = contaCorrenteForm.toContaCorrente();
        repositorioContaCorrente.fechar(contaCorrente);
        return ResponseEntity.ok("Conta fechada com sucesso");
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
