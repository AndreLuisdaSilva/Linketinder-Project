package com.example.demo.exerciciosgroovy.Linketinder.service

import com.example.demo.exerciciosgroovy.Linketinder.dao.CompanyDAO
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa

class CompanyService {
    private CompanyDAO companyDAO

    CompanyService(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO
    }

    Empresa registerCompany(Map companyData) {
        def empresa = new Empresa(companyData)
        companyDAO.save(empresa)
        return empresa
    }

    List<Empresa> getAllCompanies() {
        return companyDAO.findAll()
    }
}
