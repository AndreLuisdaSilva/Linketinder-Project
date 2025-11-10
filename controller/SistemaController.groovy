package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class SistemaController {

    private Menu view
    private Candidate Candidate
    private Company Company
    private Vacancy Vacancy
    private boolean running = true

    SistemaController(Menu view, 
                        Candidate Candidate, 
                        Company Company, 
                        Vacancy Vacancy) {
        this.view = view
        this.Candidate = Candidate
        this.Company = Company
        this.Vacancy = Vacancy
    }

    void iniciar() {
        while (running) {

            int opcao = view.exibirMenuPrincipal() 

            switch (opcao) {
                case 1:
                    Candidate.iniciarGerenciamento()
                    break
                case 2:
                    Company.iniciarGerenciamento()
                    break
                case 3:
                    Vacancy.iniciarGerenciamento()
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
}