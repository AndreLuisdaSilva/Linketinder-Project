package com.example.demo.exerciciosgroovy.Linketinder.model

import groovy.transform.ToString

abstract class Pessoa implements IPessoa {
    String name
    String email
    String state
    String cep
    List<String> skills = []
    String description   // adicionado para o resumo

    String getResumo() {
        def skillsList = skills?.join(', ') ?: '-'
        return """
        Name: ${name}
        E-mail: ${email}
        State: ${state}
        CEP: ${cep}
        Description: ${description}
        Skills: ${skillsList}
        """.stripIndent().trim()
    }
}
