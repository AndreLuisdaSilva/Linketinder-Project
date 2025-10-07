
package com.example.demo.exerciciosgroovy.Linketinder.model

import groovy.transform.ToString

abstract class Pessoa implements IPessoa {
    int id
    String name
    String email
    String state
    String cep
    String password
    List<String> skills = []
    String description

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
