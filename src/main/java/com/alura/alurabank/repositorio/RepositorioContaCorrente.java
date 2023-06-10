package com.alura.alurabank.repositorio;

import com.alura.alurabank.dominio.ContaCorrente;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
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
}
