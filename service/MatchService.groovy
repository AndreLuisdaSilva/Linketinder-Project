package com.example.demo.exerciciosgroovy.Linketinder.service

import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateCompanyDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateVacancyDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.CompanyCandidateDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.VacancyDAO
import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy

import java.util.Optional

class MatchService {

    static boolean isMatch(int candidateId, int entityId, String entityType) {
        if (entityType == 'vacancy') {
            Optional<Vacancy> optionalVacancy = VacancyDAO.findById(entityId as Long)
            
            if (optionalVacancy.isEmpty()) {
                println "Vaga não encontrada para ID: ${entityId}"
                return false
            }

            Vacancy vacancy = optionalVacancy.get()
            int companyId = vacancy.companyId 

            boolean candidateLikedVacancy = CandidateVacancyDAO.hasLiked(candidateId, entityId)
            boolean companyLikedCandidate = CompanyCandidateDAO.hasLiked(companyId, candidateId)

            return candidateLikedVacancy && companyLikedCandidate

        } else if (entityType == 'company') {
            
            boolean candidateLikedCompany = CandidateCompanyDAO.hasLiked(candidateId, entityId)
            boolean companyLikedCandidate = CompanyCandidateDAO.hasLiked(entityId, candidateId)

            return candidateLikedCompany && companyLikedCandidate
        }
        
        return false
    }
}