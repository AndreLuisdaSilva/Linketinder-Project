package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class SistemaController {

    private Menu view
    private List<Candidato> candidatos = []
    private List<Empresa> empresas = []

    SistemaController(Menu view) {
        this.view = view
        inicializarDados()
    }

    void iniciar() {
        while (true) {
            int opcao = view.exibirMenuPrincipal()

            switch (opcao) {
                case 1:
                    view.exibirCandidatos(candidatos)
                    break
                case 2:
                    view.exibirEmpresas(empresas)
                    break
                case 3:
                    cadastrarNovoCandidato()
                    break
                case 4:
                    cadastrarNovaEmpresa()
                    break
                case 0:
                    view.exibirMensagem("Obrigado por usar o Linketinder! Até mais!")
                    System.exit(0)
                default:
                    view.exibirMensagem("Opção inválida. Tente novamente.")
                    break
            }
        }
    }

    private void inicializarDados() {
        candidatos << new Candidato(nome: "André Silva", email: "andre.s@email.com", cpf: "111.111.111-11", idade: 28, estado: "SP", cep: "14000-001", descricaoPessoal: "Dev Java Sênior", competencias: ["Java", "Spring Framework", "SQL"])
        candidatos << new Candidato(nome: "Beatriz Costa", email: "bia.costa@email.com", cpf: "222.222.222-22", idade: 22, estado: "RJ", cep: "20000-002", descricaoPessoal: "Frontend Developer", competencias: ["Angular", "TypeScript", "HTML", "CSS"])
        candidatos << new Candidato(nome: "Carlos Pereira", email: "carlos.p@email.com", cpf: "333.333.333-33", idade: 35, estado: "MG", cep: "30000-003", descricaoPessoal: "Engenheiro de Dados", competencias: ["Python", "Apache Spark", "Airflow"])
        candidatos << new Candidato(nome: "Daniela Santos", email: "dani.s@email.com", cpf: "444.444.444-44", idade: 25, estado: "BA", cep: "40000-004", descricaoPessoal: "QA Pleno", competencias: ["Cypress", "Selenium", "Groovy", "JUnit"])
        candidatos << new Candidato(nome: "Eduardo Lima", email: "edu.lima@email.com", cpf: "555.555.555-55", idade: 31, estado: "SC", cep: "88000-005", descricaoPessoal: "Backend com foco em Groovy/Grails", competencias: ["Groovy", "Grails", "Java", "Micronaut"])

        empresas << new Empresa(nome: "Arroz-Gostoso", email: "contato@arrozgostoso.com", cnpj: "01.001.001/0001-01", pais: "Brasil", estado: "SP", cep: "14100-001", descricao: "Líder no setor de alimentos.", competencias: ["Java", "Spring Framework", "AWS"])
        empresas << new Empresa(nome: "Império do Boliche", email: "rh@imperioboliche.com", cnpj: "02.002.002/0001-02", pais: "Brasil", estado: "RJ", cep: "21100-002", descricao: "Rede de entretenimento familiar.", competencias: ["Angular", "Node.js", "MongoDB"])
        empresas << new Empresa(nome: "Tech Solutions", email: "vagas@techsolutions.com", cnpj: "03.003.003/0001-03", pais: "Brasil", estado: "MG", cep: "31100-003", descricao: "Consultoria de TI.", competencias: ["Python", "Django", "PostgreSQL"])
        empresas << new Empresa(nome: "Data Insights", email: "talentos@datainsights.com", cnpj: "04.004.004/0001-04", pais: "Brasil", estado: "SP", cep: "04500-004", descricao: "Análise de dados para o mercado.", competencias: ["Python", "SQL", "PowerBI", "Airflow"])
        empresas << new Empresa(nome: "Inova Web", email: "dev@inovaweb.com", cnpj: "05.005.005/0001-05", pais: "Brasil", estado: "PR", cep: "80100-005", descricao: "Desenvolvimento de aplicações web.", competencias: ["Groovy", "Grails", "React", "Java"])
    }


    private void cadastrarNovoCandidato() {
        Map dadosCandidato = view.lerDadosNovoCandidato()
        candidatos << new Candidato(dadosCandidato)
        view.exibirMensagem("Candidato cadastrado com sucesso!")
    }

    private void cadastrarNovaEmpresa() {
        Map dadosEmpresa = view.lerDadosNovaEmpresa()
        empresas << new Empresa(dadosEmpresa)
        view.exibirMensagem("Empresa cadastrada com sucesso!")
    }
}
