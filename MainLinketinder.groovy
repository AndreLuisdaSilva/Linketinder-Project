package com.example.demo.exerciciosgroovy.Linketinder

import com.example.demo.exerciciosgroovy.Linketinder.controller.SistemaController
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
        def controller = new SistemaController(menu, candidateService, companyService, vacancyService)
        controller.iniciar()
    }
}
