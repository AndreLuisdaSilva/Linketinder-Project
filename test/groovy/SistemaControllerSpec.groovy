package com.example.demo.exerciciosgroovy.Linketinder.controller

import spock.lang.Specification
import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class SistemaControllerSpec extends Specification {

    def "cadastrar novo candidato"() {
        given:
        def menu = Mock(Menu)
        menu.lerDadosNovoCandidato() >> [nome: "Teste", email: "teste@email.com", cpf: "000.000.000-00", idade: 30, estado: "SP", cep: "00000-000", descricaoPessoal: "Dev", competencias: ["Java"]]
        def sistema = new SistemaController(menu)

        when:
        sistema.cadastrarNovoCandidato()

        then:
        sistema.candidatos.find { it.nome == "Teste" } != null
    }

    def "cadastrar nova empresa"() {
        given:
        def menu = Mock(Menu)
        menu.lerDadosNovaEmpresa() >> [nome: "EmpresaTeste", email: "empresa@email.com", cnpj: "00.000.000/0001-00", pais: "Brasil", estado: "SP", cep: "00000-000", descricao: "Empresa de teste", competencias: ["Java"]]
        def sistema = new SistemaController(menu)

        when:
        sistema.cadastrarNovaEmpresa()

        then:
        sistema.empresas.find { it.nome == "EmpresaTeste" } != null
    }
}
