package com.example.demo.exerciciosgroovy.Linketinder.service

import com.example.demo.exerciciosgroovy.Linketinder.dao.VacancyDAO
import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy
import java.util.Optional

class VacancyService {
    private VacancyDAO vacancyDAO

    VacancyService() {
        this.vacancyDAO = new VacancyDAO()
    }

    Vacancy registerVacancy(Map vacancyData) {
        Vacancy vacancy = new Vacancy(vacancyData)
        vacancyDAO.save(vacancy)
        return vacancy
    }

    Vacancy findVacancyById(Long id) {
        Optional<Vacancy> optionalVacancy = vacancyDAO.findById(id)
        return optionalVacancy.orElse(null)
    }

    List<Vacancy> getVacanciesByCompanyId(int companyId) {
        return vacancyDAO.findByCompanyId(companyId)
    }

    List<Vacancy> getAllVacancies() {
        return vacancyDAO.findAll()
    }

    Vacancy updateVacancy(Long id, Map novosDados) {
        Vacancy vacancy = findVacancyById(id)
        if (vacancy) {
            vacancy.title = novosDados.title ?: vacancy.title
            vacancy.description = novosDados.description ?: vacancy.description
            vacancy.location = novosDados.location ?: vacancy.location
            vacancyDAO.update(vacancy)
        }
        return vacancy
    }

    boolean deleteVacancy(Long id) {
        return vacancyDAO.delete(id)
    }
}
