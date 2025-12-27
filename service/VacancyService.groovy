package com.example.demo.exerciciosgroovy.Linketinder.service

import com.example.demo.exerciciosgroovy.Linketinder.dao.VacancyDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.VacancySkillDAO // Importante!
import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy
import com.example.demo.exerciciosgroovy.Linketinder.model.Skill
import java.util.Optional

class VacancyService {
    private VacancyDAO vacancyDAO = new VacancyDAO()

    Vacancy registerVacancy(Map vacancyData) {
        Vacancy vacancy = new Vacancy(vacancyData)
        vacancyDAO.save(vacancy)
        return vacancy
    }

    Vacancy findVacancyById(Long id) {
        Optional<Vacancy> optionalVacancy = vacancyDAO.findById(id)
        if (optionalVacancy.isPresent()) {
            Vacancy vacancy = optionalVacancy.get()
            vacancy.skills = getSkillsByVacancyId(id)
            return vacancy
        }
        return null
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
            return vacancy
        }
        return null
    }

    boolean deleteVacancy(Long id) {
        return vacancyDAO.delete(id)
    }

    void addSkillToVacancy(Long vacancyId, Long skillId) {
        VacancySkillDAO.addSkillToVacancy(vacancyId.toInteger(), skillId.toInteger())
    }

    void removeSkillFromVacancy(Long vacancyId, Long skillId) {
        VacancySkillDAO.removeSkillFromVacancy(vacancyId.toInteger(), skillId.toInteger())
    }

    List<Skill> getSkillsByVacancyId(Long vacancyId) {
        return VacancySkillDAO.findSkillsByVacancyId(vacancyId.toInteger())
    }
}