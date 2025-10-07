
package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.CompanyDAO
import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class SistemaController {

    private Menu view
    private CandidateDAO candidateDAO
    private CompanyDAO companyDAO

    SistemaController(Menu view, CandidateDAO candidateDAO, CompanyDAO companyDAO) {
        this.view = view
        this.candidateDAO = candidateDAO
        this.companyDAO = companyDAO
    }

    void iniciar() {
        while (true) {
            int opcao = view.exibirMenuPrincipal()

            switch (opcao) {
                case 1:
                    view.exibirCandidatos(candidateDAO.findAll())
                    break
                case 2:
                    view.exibirEmpresas(companyDAO.findAll())
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

    private void cadastrarNovoCandidato() {
        Map dadosCandidato = view.lerDadosNovoCandidato()
        def candidato = new Candidato(dadosCandidato)
        candidateDAO.save(candidato)
        view.exibirMensagem("Candidato cadastrado com sucesso!")
    }

    private void cadastrarNovaEmpresa() {
        Map dadosEmpresa = view.lerDadosNovaEmpresa()
        def empresa = new Empresa(dadosEmpresa)
        companyDAO.save(empresa)
        view.exibirMensagem("Empresa cadastrada com sucesso!")
    }
}
