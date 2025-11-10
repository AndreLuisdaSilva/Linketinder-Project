
package com.example.demo.exerciciosgroovy.Linketinder.view

import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy
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
        println "5. Atualizar um Candidato"
        println "6. Buscar um Candidato por id"
        println "7. Cadastrar Nova Vaga"
        println "8. Listar Vagas"
        println "9. Atualizar Vaga"
        println "10. Excluir Vaga"
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
            println "Candidato #${i + 1} (ID: ${candidato.id ?: 'N/A'})"
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

    Map lerDadosAtualizacaoCandidato() {
        println "\n--- ATUALIZAÇÃO DE CANDIDATO ---"

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

    Long lerIdCandidato() {
        print "Digite o ID do candidato para buscar: "
        def idInput = scanner.nextLine()
        try {
            return idInput?.trim() ? idInput.trim() as Long : null
        } catch (NumberFormatException e) {
            println "ID inválido. Por favor, digite um número."
            return null
        }
    }
    
    // New methods for Vacancy CRUD
    Map lerDadosNovaVaga() {
        println "\n--- CADASTRO DE NOVA VAGA ---"
        print "Título da Vaga: "; def title = scanner.nextLine()
        print "Descrição da Vaga: "; def description = scanner.nextLine()
        print "ID da Empresa: "; def companyId = scanner.nextLine() as Integer
        print "Localização da Vaga: "; def location = scanner.nextLine()

        return [
            title: title, description: description, companyId: companyId, location: location
        ]
    }

    void exibirVagas(List<Vacancy> vacancies) {
        println "\n--- LISTA DE VAGAS ---"
        if (vacancies.isEmpty()) {
            println "Nenhuma vaga cadastrada."
            return
        }
        vacancies.eachWithIndex { vacancy, i ->
            println "Vaga #${i + 1} (ID: ${vacancy.id ?: 'N/A'})"
            println "  Título: ${vacancy.title}"
            println "  Descrição: ${vacancy.description}"
            println "  ID da Empresa: ${vacancy.companyId}"
            println "  Localização: ${vacancy.location}"
            println "--------------------------------"
        }
    }

    Map lerDadosAtualizacaoVaga() {
        println "\n--- ATUALIZAÇÃO DE VAGA ---"
        print "Novo Título da Vaga: "; def title = scanner.nextLine()
        print "Nova Descrição da Vaga: "; def description = scanner.nextLine()
        print "Nova Localização da Vaga: "; def location = scanner.nextLine()

        return [
            title: title, description: description, location: location
        ]
    }

    int lerIdVaga() {
        print "Digite o ID da vaga: "
        def idInput = scanner.nextLine()
        try {
            return idInput?.trim() ? idInput.trim() as Integer : -1
        } catch (NumberFormatException e) {
            println "ID inválido. Por favor, digite um número."
            return -1
        }
    }

    void exibirMensagem(String mensagem) {
        println mensagem
    }
}
