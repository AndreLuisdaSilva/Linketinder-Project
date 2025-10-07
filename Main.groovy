package com.example.demo.exerciciosgroovy.Linketinder

import com.example.demo.exerciciosgroovy.Linketinder.controller.SistemaController
import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.CompanyDAO
import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class Main {

    static void main(String[] args) {
        def menu = new Menu()
        def candidateDAO = new CandidateDAO()
        def companyDAO = new CompanyDAO()
        def controller = new SistemaController(menu, candidateDAO, companyDAO)
        controller.iniciar()
    }
}
