
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
            println "  Nome: ${candidato.nome}"
            println "  Email: ${candidato.email}"
            println "  Estado: ${candidato.estado}"
            println "  Competências: ${candidato.competencias.join(', ')}"
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
            println "  Nome: ${empresa.nome}"
            println "  Email: ${empresa.email}"
            println "  País: ${empresa.pais}"
            println "  Competências buscadas: ${empresa.competencias.join(', ')}"
            println "--------------------------------"
        }
    }

    Map lerDadosNovoCandidato() {
        println "\n--- CADASTRO DE NOVO CANDIDATO ---"
        print "Nome: "; def nome = scanner.nextLine()
        print "Email: "; def email = scanner.nextLine()
        print "CPF: "; def cpf = scanner.nextLine()
        print "Idade: "; def idade = scanner.nextLine()
        print "Estado (sigla): "; def estado = scanner.nextLine()
        print "CEP: "; def cep = scanner.nextLine()
        print "Descrição Pessoal: "; def descricao = scanner.nextLine()
        print "Competências (separadas por vírgula): "; def comps = scanner.nextLine()
        
        return [
            nome: nome, email: email, cpf: cpf, idade: idade as Integer, 
            estado: estado, cep: cep, descricaoPessoal: descricao,
            competencias: comps.split(',').collect { it.trim() }
        ]
    }

    Map lerDadosNovaEmpresa() {
        println "\n--- CADASTRO DE NOVA EMPRESA ---"
        print "Nome da Empresa: "; def nome = scanner.nextLine()
        print "Email Corporativo: "; def email = scanner.nextLine()
        print "CNPJ: "; def cnpj = scanner.nextLine()
        print "País: "; def pais = scanner.nextLine()
        print "Estado (sigla): "; def estado = scanner.nextLine()
        print "CEP: "; def cep = scanner.nextLine()
        print "Descrição da Empresa: "; def descricao = scanner.nextLine()
        print "Competências buscadas (separadas por vírgula): "; def comps = scanner.nextLine()

        return [
            nome: nome, email: email, cnpj: cnpj, pais: pais,
            estado: estado, cep: cep, descricao: descricao,
            competencias: comps.split(',').collect { it.trim() }
        ]
    }
    
    void exibirMensagem(String mensagem) {
        println mensagem
    }
}
