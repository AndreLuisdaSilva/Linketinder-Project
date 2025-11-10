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
        println "1. Gerenciar Candidatos"
        println "2. Gerenciar Empresas"
        println "3. Gerenciar Vagas"
        println "0. Sair"
        print "Escolha uma opção: "
        
        return lerOpcao()
    }

    int exibirMenuCandidatos() {
        println "\n--- GERENCIAR CANDIDATOS ---"
        println "1. Listar Candidatos"
        println "2. Cadastrar Novo Candidato"
        println "3. Atualizar um Candidato"
        println "4. Buscar um Candidato por ID"
        println "5. Excluir Candidato"
        println "0. Voltar ao menu principal"
        print "Escolha uma opção: "
        
        return lerOpcao()
    }

    int exibirMenuEmpresas() {
        println "\n--- GERENCIAR EMPRESAS ---"
        println "1. Listar Empresas"
        println "2. Cadastrar Nova Empresa"
        println "3. Buscar Empresa por Nome"
        println "4. Excluir Empresa"
        println "0. Voltar ao menu principal"
        print "Escolha uma opção: "
        
        return lerOpcao()
    }

    int exibirMenuVagas() {
        println "\n--- GERENCIAR VAGAS ---"
        println "1. Listar Vagas"
        println "2. Cadastrar Nova Vaga"
        println "3. Atualizar Vaga"
        println "4. Excluir Vaga"
        println "0. Voltar ao menu principal"
        print "Escolha uma opção: "
        
        return lerOpcao()
    }

    private int lerOpcao() {
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
            println "Empresa #${i + 1} (ID: ${empresa.id ?: 'N/A'})"
            println "  Nome: ${empresa.name}"
            println "  Email: ${empresa.email}"
            println "  País: ${empresa.country}"
            println "  Skills buscadas: ${empresa.skills?.join(', ') ?: '-'}"
            println "--------------------------------"
        }
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
            name: name, email: email, cpf: cpf, age: age ? age.toInteger() : 0, // Adicionado
            state: state, cep: cep, password: password, personalDescription: personalDescription,
            skills: comps ? comps.split(',').collect { it.trim() } : []
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
            skills: comps ? comps.split(',').collect { it.trim() } : []
        ]
    }

    Map lerDadosAtualizacaoCandidato() {
        println "\n--- ATUALIZAÇÃO DE CANDIDATO ---"
        println "(Deixe em branco para manter o valor atual)"
        print "Nome: "; def name = scanner.nextLine()
        print "Email: "; def email = scanner.nextLine()
        print "CPF: "; def cpf = scanner.nextLine()
        print "Idade: "; def age = scanner.nextLine()
        print "Estado (sigla): "; def state = scanner.nextLine()
        print "CEP: "; def cep = scanner.nextLine()
        print "Descrição Pessoal: "; def personalDescription = scanner.nextLine()
        print "Skills (separadas por vírgula): "; def comps = scanner.nextLine()
        
        def dados = [:]
        if (name) dados.name = name
        if (email) dados.email = email
        if (cpf) dados.cpf = cpf
        if (age) dados.age = age.toInteger()
        if (state) dados.state = state
        if (cep) dados.cep = cep
        if (personalDescription) dados.personalDescription = personalDescription
        if (comps) dados.skills = comps.split(',').collect { it.trim() }
        
        return dados
    }

    Map lerDadosNovaVaga() {
        println "\n--- CADASTRO DE NOVA VAGA ---"
        print "Título da Vaga: "; def title = scanner.nextLine()
        print "Descrição da Vaga: "; def description = scanner.nextLine()
        print "ID da Empresa: "; def companyId = scanner.nextLine()
        print "Localização da Vaga: "; def location = scanner.nextLine()

        return [
            title: title, 
            description: description, 
            companyId: companyId ? companyId.toInteger() : null, 
            location: location
        ]
    }

    Map lerDadosAtualizacaoVaga() {
        println "\n--- ATUALIZAÇÃO DE VAGA ---"
        println "(Deixe em branco para manter o valor atual)"
        print "Novo Título da Vaga: "; def title = scanner.nextLine()
        print "Nova Descrição da Vaga: "; def description = scanner.nextLine()
        print "Nova Localização da Vaga: "; def location = scanner.nextLine()

        def dados = [:]
        if (title) dados.title = title
        if (description) dados.description = description
        if (location) dados.location = location
        
        return dados
    }


    Long lerIdCandidato() {
        print "Digite o ID do candidato: "
        def idInput = scanner.nextLine()
        try {
            return idInput?.trim() ? idInput.trim() as Long : null
        } catch (NumberFormatException e) {
            println "ID inválido. Por favor, digite um número."
            return null
        }
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


    Long pedirIdParaExclusao(String tipo) {
        print "Digite o ID do ${tipo} para EXCLUIR: "
        def idInput = scanner.nextLine()
        try {
            return idInput?.trim() ? idInput.trim() as Long : null
        } catch (NumberFormatException e) {
            println "ID inválido. Por favor, digite um número."
            return null
        }
    }


    String pedirNomeParaBusca() {
        print "Digite o nome (ou parte do nome) para buscar: "
        return scanner.nextLine()
    }

    void exibirMensagem(String mensagem) {
        println "\n>> ${mensagem}"
    }
}