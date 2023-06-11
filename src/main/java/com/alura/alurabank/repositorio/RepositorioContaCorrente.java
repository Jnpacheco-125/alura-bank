package com.alura.alurabank.repositorio;

import com.alura.alurabank.dominio.ContaCorrente;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Repository
public class RepositorioContaCorrente {

    private Set<ContaCorrente> contas;
    public RepositorioContaCorrente(){
        contas = new HashSet<>();
    }
    public void salvar(ContaCorrente conta) {
        contas.add(conta);
    }

    public Optional<ContaCorrente> buscar(String banco, String agencia, String numero){
        return contas.stream()
                .filter(contaCorrente -> contaCorrente.identificadaPor(banco, agencia, numero))
                .findFirst();
    }

    public void fechar(ContaCorrente conta) {
        contas.remove(conta);
    }
}
