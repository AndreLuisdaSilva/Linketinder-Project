package com.example.demo.exerciciosgroovy.Linketinder.service

import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateSkillDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.VacancyDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.VacancySkillDAO
import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy

class MatchService {

    static List<Vacancy> findMatches(int candidateId) {
        def candidateSkills = CandidateSkillDAO.findSkillsByCandidateId(candidateId)
        def allVacancies = VacancyDAO.findAll()
        def matchedVacancies = []

        allVacancies.each { vacancy ->
            def vacancySkills = VacancySkillDAO.findSkillsByVacancyId(vacancy.id)
            def commonSkills = candidateSkills.intersect(vacancySkills)
            if (!commonSkills.isEmpty()) {
                matchedVacancies.add(vacancy)
            }
        }

        return matchedVacancies
    }
}
