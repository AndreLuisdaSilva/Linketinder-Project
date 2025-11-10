package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy
import com.example.demo.exerciciosgroovy.Linketinder.service.CandidateService
import com.example.demo.exerciciosgroovy.Linketinder.service.CompanyService
import com.example.demo.exerciciosgroovy.Linketinder.service.VacancyService
import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class SistemaController {

    private Menu view
    private CandidateService candidateService
    private CompanyService companyService
    private VacancyService vacancyService 
    private boolean running = true

    SistemaController(Menu view, CandidateService candidateService, CompanyService companyService, VacancyService vacancyService) {
        this.view = view
        this.candidateService = candidateService
        this.companyService = companyService
        this.vacancyService = vacancyService 
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
                case 5:
                    atualizarCandidato()
                    break
                case 6:
                    buscarCandidatoPorId()
                    break
                case 7: 
                    cadastrarNovaVaga()
                    break
                case 8: 
                    exibirVagas()
                    break
                case 9: 
                    atualizarVaga()
                    break
                case 10: 
                    excluirVaga()
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

    private void atualizarCandidato() {
        view.exibirCandidatos(candidateService.getAllCandidates())
        Long idCandidatoParaAtualizar = view.lerIdCandidato()

        Candidato candidatoExistente = candidateService.findCandidateById(idCandidatoParaAtualizar)

        Map novosDados = view.lerDadosAtualizacaoCandidato()

        Candidato candidatoAtualizado = candidateService.updateCandidate(idCandidatoParaAtualizar, novosDados)

       if (candidatoAtualizado) {
            view.exibirMensagem("Candidato atualizado com sucesso!")
        } else {
            view.exibirMensagem("Candidato com ID ${idCandidatoParaAtualizar} não encontrado.")
        }
    }

    void buscarCandidatoPorId() {
        Long idDoCandidato = view.lerIdCandidato()
        print(idDoCandidato)
        Candidato candidatoEncontrado = candidateService.findCandidateById(idDoCandidato)
         if (candidatoEncontrado) {
            view.exibirCandidatos([candidatoEncontrado])
        } else {
            view.exibirMensagem("Candidato com ID ${idDoCandidato} não encontrado.")
        }
        view.exibirMensagem("Busca de candidato por id.")
    }

    void buscarEmpresaPorNome() {
        String nomeBusca = view.pedirNomeParaBusca()
        List<Empresa> empresasEncontradas = companyService.findCompaniesByName(nomeBusca)
        if (!empresasEncontradas.isEmpty()) {
            view.exibirEmpresas(empresasEncontradas)
        } else {
            view.exibirMensagem("Nenhuma empresa encontrada com o nome '${nomeBusca}'.")
        }
    }

    void excluirCandidato() {
        view.exibirCandidatos(candidateService.getAllCandidates())
        Long idParaExcluir = view.pedirIdParaExclusao("candidato")
        if (candidateService.deleteCandidate(idParaExcluir)) {
            view.exibirMensagem("Candidato excluído com sucesso.")
        } else {
            view.exibirMensagem("Candidato não encontrado ou não pode ser excluído.")
        }
    }

    void excluirEmpresa() {
        // TODO: Implementar exclusão de empresa
        view.exibirMensagem("Funcionalidade de exclusão de empresa ainda não implementada.")
    }

    void exibirCandidatos() {
        view.exibirCandidatos(candidateService.getAllCandidates())
    }

    void exibirEmpresas() {
        view.exibirEmpresas(companyService.getAllCompanies())
    }

    private void cadastrarNovaVaga() {
        Map dadosVaga = view.lerDadosNovaVaga()
        if (dadosVaga.companyId) {
            vacancyService.createVacancy(dadosVaga.title, dadosVaga.description, dadosVaga.companyId, dadosVaga.location)
            view.exibirMensagem("Vaga cadastrada com sucesso!")
        } else {
            view.exibirMensagem("É necessário informar o ID da empresa para cadastrar uma vaga.")
        }
    }

    void exibirVagas() {
        view.exibirVagas(vacancyService.getAllVacancies())
    }

    private void atualizarVaga() {
        view.exibirVagas(vacancyService.getAllVacancies())
        int idVagaParaAtualizar = view.lerIdVaga()

        Vacancy vagaExistente = vacancyService.getVacancyById(idVagaParaAtualizar)

        if (vagaExistente) {
            Map novosDados = view.lerDadosAtualizacaoVaga()
            Vacancy vagaAtualizada = vacancyService.updateVacancy(idVagaParaAtualizar, novosDados.title, novosDados.description, novosDados.location)
            if (vagaAtualizada) {
                view.exibirMensagem("Vaga atualizada com sucesso!")
            } else {
                view.exibirMensagem("Erro ao atualizar vaga.")
            }
        } else {
            view.exibirMensagem("Vaga com ID ${idVagaParaAtualizar} não encontrada.")
        }
    }

    void excluirVaga() {
        view.exibirVagas(vacancyService.getAllVacancies())
        int idParaExcluir = view.lerIdVaga()
        if (vacancyService.deleteVacancy(idParaExcluir)) {
            view.exibirMensagem("Vaga excluída com sucesso.")
        } else {
            view.exibirMensagem("Vaga não encontrada ou não pode ser excluída.")
        }
    }

}
