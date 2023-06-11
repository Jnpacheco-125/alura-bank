package com.alura.alurabank.config;


import com.alura.alurabank.controller.form.ContaCorrenteForm;
import com.alura.alurabank.dominio.ContaCorrente;
import com.googlecode.jmapper.JMapper;
import com.googlecode.jmapper.api.JMapperAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.googlecode.jmapper.api.JMapperAPI.attribute;

@Configuration
public class JMapperBean {
    //@Bean
    public JMapper<ContaCorrente, ContaCorrenteForm> contaCorrenteMapper(){
        JMapperAPI jMapperAPI = new JMapperAPI()
        .add(JMapperAPI.mappedClass(ContaCorrente.class)
                .add(attribute("banco").value("banco"))
                .add(attribute("agencia").value("agencia"))
                .add(attribute("numero").value("numero")));
        return new JMapper<>(ContaCorrente.class, ContaCorrenteForm.class, jMapperAPI);
    }
}
