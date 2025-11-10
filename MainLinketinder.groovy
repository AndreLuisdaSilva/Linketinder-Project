package com.example.demo.exerciciosgroovy.Linketinder

// Importe os novos controllers
import com.example.demo.exerciciosgroovy.Linketinder.controller.Candidate
import com.example.demo.exerciciosgroovy.Linketinder.controller.Company
import com.example.demo.exerciciosgroovy.Linketinder.controller.SistemaController
import com.example.demo.exerciciosgroovy.Linketinder.controller.Vacancy

import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.CompanyDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.VacancyDAO
import com.example.demo.exerciciosgroovy.Linketinder.service.CandidateService
import com.example.demo.exerciciosgroovy.Linketinder.service.CompanyService
import com.example.demo.exerciciosgroovy.Linketinder.service.VacancyService
import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class MainLinketinder {

    static void main(String[] args) {
        def menu = new Menu()

        def candidateDAO = new CandidateDAO()
        def companyDAO = new CompanyDAO()
        def vacancyDAO = new VacancyDAO()

        def candidateService = new CandidateService(candidateDAO)
        def companyService = new CompanyService(companyDAO)
        def vacancyService = new VacancyService(vacancyDAO)


        def Candidate = new Candidate(menu, candidateService)
        def Company = new Company(menu, companyService)
        def Vacancy = new Vacancy(menu, vacancyService)

        def sistemaController = new SistemaController(
                menu, 
                Candidate, 
                Company, 
                Vacancy
        )

        sistemaController.iniciar()
    }
}