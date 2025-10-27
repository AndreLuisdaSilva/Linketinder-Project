package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
import com.example.demo.exerciciosgroovy.Linketinder.service.CandidateService
import com.example.demo.exerciciosgroovy.Linketinder.service.CompanyService
import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class SistemaController {

    private Menu view
    private CandidateService candidateService
    private CompanyService companyService
    private boolean running = true

    SistemaController(Menu view, CandidateService candidateService, CompanyService companyService) {
        this.view = view
        this.candidateService = candidateService
        this.companyService = companyService
    }

    void iniciar() {
        while (running) {
            int opcao = view.exibirMenuPrincipal()

            switch (opcao) {
                case 1:
                    view.exibirCandidatos(candidateService.getAllCandidates())
                    break
                case 2:
                    view.exibirEmpresas(companyService.getAllCompanies())
                    break
                case 3:
                    cadastrarNovoCandidato()
                    break
                case 4:
                    cadastrarNovaEmpresa()
                    break
                case 0:
                    view.exibirMensagem("Obrigado por usar o Linketinder! Até mais!")
                    running = false
                    break
                default:
                    view.exibirMensagem("Opção inválida. Tente novamente.")
                    break
            }
        }
    }

    private void cadastrarNovoCandidato() {
        Map dadosCandidato = view.lerDadosNovoCandidato()
        candidateService.registerCandidate(dadosCandidato)
        view.exibirMensagem("Candidato cadastrado com sucesso!")
    }

    private void cadastrarNovaEmpresa() {
        Map dadosEmpresa = view.lerDadosNovaEmpresa()
        companyService.registerCompany(dadosEmpresa)
        view.exibirMensagem("Empresa cadastrada com sucesso!")
    }
}
