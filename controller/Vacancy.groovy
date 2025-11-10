package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy
import com.example.demo.exerciciosgroovy.Linketinder.service.VacancyService
import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class Vacancy {

    private Menu view
    private VacancyService vacancyService
    private boolean managing = true

    Vacancy(Menu view, VacancyService vacancyService) {
        this.view = view
        this.vacancyService = vacancyService
    }

    void iniciarGerenciamento() {
        managing = true
        while (managing) {
            int opcao = view.exibirMenuVagas()

            switch (opcao) {
                case 1:
                    exibirVagas()
                    break
                case 2:
                    cadastrarNovaVaga()
                    break
                case 3:
                    atualizarVaga()
                    break
                case 4:
                    excluirVaga()
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

    private void cadastrarNovaVaga() {
        Map dadosVaga = view.lerDadosNovaVaga()
        if (dadosVaga.companyId) {
            vacancyService.createVacancy(dadosVaga.title, dadosVaga.description, dadosVaga.companyId, dadosVaga.location)
            view.exibirMensagem("Vaga cadastrada com sucesso!")
        } else {
            view.exibirMensagem("É necessário informar o ID da empresa para cadastrar uma vaga.")
        }
    }

    private void exibirVagas() {
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

    private void excluirVaga() {
        view.exibirVagas(vacancyService.getAllVacancies())
        int idParaExcluir = view.lerIdVaga()
        if (vacancyService.deleteVacancy(idParaExcluir)) {
            view.exibirMensagem("Vaga excluída com sucesso.")
        } else {
            view.exibirMensagem("Vaga não encontrada ou não pode ser excluída.")
        }
    }
}