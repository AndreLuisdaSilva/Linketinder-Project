package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import com.example.demo.exerciciosgroovy.Linketinder.service.CandidateService
import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class Candidate {

    private Menu view
    private CandidateService candidateService
    private boolean managing = true

    Candidate(Menu view, CandidateService candidateService) {
        this.view = view
        this.candidateService = candidateService
    }

    void iniciarGerenciamento() {
        managing = true
        while (managing) {
            int opcao = view.exibirMenuCandidatos() 

            switch (opcao) {
                case 1:
                    exibirCandidatos()
                    break
                case 2:
                    cadastrarNovoCandidato()
                    break
                case 3:
                    atualizarCandidato()
                    break
                case 4:
                    buscarCandidatoPorId()
                    break
                case 5:
                    excluirCandidato()
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
    
    private void exibirCandidatos() {
        view.exibirCandidatos(candidateService.getAllCandidates())
    }

    private void cadastrarNovoCandidato() {
        Map dadosCandidato = view.lerDadosNovoCandidato()
        candidateService.registerCandidate(dadosCandidato)
        view.exibirMensagem("Candidato cadastrado com sucesso!")
    }

    private void atualizarCandidato() {
        view.exibirCandidatos(candidateService.getAllCandidates())
        Long idCandidatoParaAtualizar = view.lerIdCandidato()
        Map novosDados = view.lerDadosAtualizacaoCandidato()
        Candidato candidatoAtualizado = candidateService.updateCandidate(idCandidatoParaAtualizar, novosDados)

        if (candidatoAtualizado) {
            view.exibirMensagem("Candidato atualizado com sucesso!")
        } else {
            view.exibirMensagem("Candidato com ID ${idCandidatoParaAtualizar} não encontrado.")
        }
    }

    private void buscarCandidatoPorId() {
        Long idDoCandidato = view.lerIdCandidato()
        Candidato candidatoEncontrado = candidateService.findCandidateById(idDoCandidato)
        if (candidatoEncontrado) {
            view.exibirCandidatos([candidatoEncontrado])
        } else {
            view.exibirMensagem("Candidato com ID ${idDoCandidato} não encontrado.")
        }
    }

    private void excluirCandidato() {
        view.exibirCandidatos(candidateService.getAllCandidates())
        Long idParaExcluir = view.pedirIdParaExclusao("candidato")
        if (candidateService.deleteCandidate(idParaExcluir)) {
            view.exibirMensagem("Candidato excluído com sucesso.")
        } else {
            view.exibirMensagem("Candidato não encontrado.")
        }
    }
}