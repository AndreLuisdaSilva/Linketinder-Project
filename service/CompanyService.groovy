package com.example.demo.exerciciosgroovy.Linketinder.service

import com.example.demo.exerciciosgroovy.Linketinder.dao.CompanyDAO
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa

class CompanyService {
    private CompanyDAO companyDAO

    CompanyService() {
        this.companyDAO = new CompanyDAO()
    }

    Empresa registerCompany(Map companyData) {
        def empresa = new Empresa(companyData)
        companyDAO.save(empresa)
        return empresa
    }

    List<Empresa> getAllCompanies() {
        return companyDAO.findAll()
    }

    Empresa findCompanyById(Long id) {
        return companyDAO.findById(id)
    }

    Empresa updateCompany(Long id, Map novosDados) {
        Empresa empresaExistente = findCompanyById(id)

        if (empresaExistente) {
            empresaExistente.name = novosDados.name ?: empresaExistente.name
            empresaExistente.description = novosDados.description ?: empresaExistente.description
            empresaExistente.country = novosDados.country ?: empresaExistente.country
            empresaExistente.password = novosDados.password ?: empresaExistente.password

            companyDAO.update(empresaExistente)
            return empresaExistente
        } else {
            return null
        }
    }

    Empresa deleteCompanyById(Long id) {
        return companyDAO.delete(id)
    }
}
