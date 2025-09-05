package com.example.demo.exerciciosgroovy.Linketinder.model
import groovy.transform.ToString

abstract class Pessoa implements IPessoa{
    String nome;
    String email;
    String estado;
    String cep
    List<String> competencias = []

    String getResumo() {
        def comp = competencias?.join(', ') ?: '-'
        return """
        Nome: ${nome}
        E-mail: ${email}
        Estado: ${estado}
        CEP: ${cep}
        Descrição: ${descricao}
        Competências: ${comp}
        """.stripIndent().trim()
    }
}