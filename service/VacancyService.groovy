package com.example.demo.exerciciosgroovy.Linketinder.service

import com.example.demo.exerciciosgroovy.Linketinder.dao.VacancyDAO
import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy

class VacancyService {
    VacancyDAO vacancyDAO 

    VacancyService(VacancyDAO vacancyDAO){
        this.vacancyDAO = vacancyDAO
    }

    Vacancy createVacancy(String title, String description, int companyId, String location) {
        Vacancy vacancy = new Vacancy(title: title, description: description, companyId: companyId, location: location)
        vacancyDAO.save(vacancy)
        return vacancy
    }

    Vacancy getVacancyById(int id) {
        return vacancyDAO.findById(id)
    }

    List<Vacancy> getVacanciesByCompanyId(int companyId) {
        return vacancyDAO.findByCompanyId(companyId)
    }

    List<Vacancy> getAllVacancies() {
        return vacancyDAO.findAll()
    }

    Vacancy updateVacancy(int id, String title, String description, String location) {
        Vacancy vacancy = vacancyDAO.findById(id)
        if (vacancy) {
            vacancy.title = title
            vacancy.description = description
            vacancy.location = location
            vacancyDAO.update(vacancy)
        }
        return vacancy
    }

    boolean deleteVacancy(int id) {
        return vacancyDAO.delete(id)
    }
}
