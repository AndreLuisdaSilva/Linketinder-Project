
package com.example.demo.exerciciosgroovy.Linketinder.view

import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
import java.util.Scanner

class Menu {

    private final Scanner scanner

    Menu() {
        this.scanner = new Scanner(System.in)
    }

    int exibirMenuPrincipal() {
        println "\n===== LINKE-TINDER DO DR. PAÇOCA ====="
        println "1. Listar Candidatos"
        println "2. Listar Empresas"
        println "3. Cadastrar Novo Candidato"
        println "4. Cadastrar Nova Empresa"
        println "0. Sair"
        print "Escolha uma opção: "
        
        try {
            def line = scanner.nextLine()
            if (line && !line.trim().isEmpty()) {
                return line.toInteger()
            }
            return -1
        } catch (NumberFormatException e) {
            return -1
        }
    }

    void exibirCandidatos(List<Candidato> candidatos) {
        println "\n--- LISTA DE CANDIDATOS ---"
        if (candidatos.isEmpty()) {
            println "Nenhum candidato cadastrado."
            return
        }
        candidatos.eachWithIndex { candidato, i ->
            println "Candidato #${i + 1}"
            println "  Nome: ${candidato.name}"
            println "  Email: ${candidato.email}"
            println "  Estado: ${candidato.state ?: '-'}"
            println "  Skills: ${candidato.skills?.join(', ') ?: '-'}"
            println "--------------------------------"
        }
    }

    void exibirEmpresas(List<Empresa> empresas) {
        println "\n--- LISTA DE EMPRESAS ---"
        if (empresas.isEmpty()) {
            println "Nenhuma empresa cadastrada."
            return
        }
        empresas.eachWithIndex { empresa, i ->
            println "Empresa #${i + 1}"
            println "  Nome: ${empresa.name}"
            println "  Email: ${empresa.email}"
            println "  País: ${empresa.country}"
            println "  Skills buscadas: ${empresa.skills?.join(', ') ?: '-'}"
            println "--------------------------------"
        }
    }

    Map lerDadosNovoCandidato() {
        println "\n--- CADASTRO DE NOVO CANDIDATO ---"
        print "Nome: "; def name = scanner.nextLine()
        print "Email: "; def email = scanner.nextLine()
        print "CPF: "; def cpf = scanner.nextLine()
        print "Idade: "; def age = scanner.nextLine()
        print "Estado (sigla): "; def state = scanner.nextLine()
        print "CEP: "; def cep = scanner.nextLine()
        print "Senha: "; def password = scanner.nextLine()
        print "Descrição Pessoal: "; def personalDescription = scanner.nextLine()
        print "Skills (separadas por vírgula): "; def comps = scanner.nextLine()
        
        return [
            name: name, email: email, cpf: cpf, age: age as Integer, 
            state: state, cep: cep, password: password, personalDescription: personalDescription,
            skills: comps.split(',').collect { it.trim() }
        ]
    }

    Map lerDadosNovaEmpresa() {
        println "\n--- CADASTRO DE NOVA EMPRESA ---"
        print "Nome da Empresa: "; def name = scanner.nextLine()
        print "Email Corporativo: "; def email = scanner.nextLine()
        print "CNPJ: "; def cnpj = scanner.nextLine()
        print "País: "; def country = scanner.nextLine()
        print "Estado (sigla): "; def state = scanner.nextLine()
        print "CEP: "; def cep = scanner.nextLine()
        print "Senha: "; def password = scanner.nextLine()
        print "Descrição da Empresa: "; def description = scanner.nextLine()
        print "Skills buscadas (separadas por vírgula): "; def comps = scanner.nextLine()

        return [
            name: name, email: email, cnpj: cnpj, country: country,
            state: state, cep: cep, password: password, description: description,
            skills: comps.split(',').collect { it.trim() }
        ]
    }
    
    void exibirMensagem(String mensagem) {
        println mensagem
    }
}
