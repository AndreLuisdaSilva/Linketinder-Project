package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
import com.example.demo.exerciciosgroovy.Linketinder.service.CompanyService
import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class Company {

    private Menu view
    private CompanyService companyService
    private boolean managing = true

    Company(Menu view, CompanyService companyService) {
        this.view = view
        this.companyService = companyService
    }

    void iniciarGerenciamento() {
        managing = true
        while (managing) {
            int opcao = view.exibirMenuEmpresas()

            switch (opcao) {
                case 1:
                    exibirEmpresas()
                    break
                case 2:
                    cadastrarNovaEmpresa()
                    break
                case 3:
                    buscarEmpresaPorNome()
                    break
                case 4:
                    excluirEmpresa()
                    break
                case 0:
                    managing = false 
                    break
                default:
                    view.exibirMensagem("Opção inválida.")
                    break
            }
        }
    }

    private void exibirEmpresas() {
        view.exibirEmpresas(companyService.getAllCompanies())
    }

    private void cadastrarNovaEmpresa() {
        Map dadosEmpresa = view.lerDadosNovaEmpresa()
        companyService.registerCompany(dadosEmpresa)
        view.exibirMensagem("Empresa cadastrada com sucesso!")
    }

    private void buscarEmpresaPorNome() {
        String nomeBusca = view.pedirNomeParaBusca()
        List<Empresa> empresasEncontradas = companyService.findCompaniesByName(nomeBusca)
        if (!empresasEncontradas.isEmpty()) {
            view.exibirEmpresas(empresasEncontradas)
        } else {
            view.exibirMensagem("Nenhuma empresa encontrada com o nome '${nomeBusca}'.")
        }
    }

    private void excluirEmpresa() {
        view.exibirMensagem("Funcionalidade de exclusão de empresa ainda não implementada.")
    }
}