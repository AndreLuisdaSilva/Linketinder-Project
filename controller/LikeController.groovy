package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateCompanyDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateVacancyDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.CompanyCandidateDAO
import com.example.demo.exerciciosgroovy.Linketinder.service.MatchService

class LikeController {

    static List<String> candidateLikesVacancy(int candidateId, int vacancyId) {
        CandidateVacancyDAO.like(candidateId, vacancyId)
        if (MatchService.isMatch(candidateId, vacancyId, 'vacancy')) {
            return ["MATCH: Candidato $candidateId deu match na Vaga $vacancyId!"]
        }
        return []
    }

    static List<String> candidateLikesCompany(int candidateId, int companyId) {
        CandidateCompanyDAO.like(candidateId, companyId)
        if (MatchService.isMatch(candidateId, companyId, 'company')) {
            return ["MATCH: Candidato $candidateId deu match direto com a Empresa $companyId!"]
        }
        return []
    }

    // 3. Empresa curte Candidato (3 parâmetros, sendo o último opcional/Integer)
    static List<String> companyLikesCandidate(int companyId, int candidateId, Integer vacancyId) {
        CompanyCandidateDAO.like(companyId, candidateId)
        
        List<String> matches = []

        if (vacancyId != null) {
            // Cenário A: Like contextualizado em uma vaga específica
            if (MatchService.isMatch(candidateId, vacancyId, 'vacancy')) {
                matches.add("MATCH: Candidato $candidateId e Vaga $vacancyId (Skills compatíveis!)")
            }
        } else {
            // Cenário B: Like genérico (verifica todas as vagas que o candidato curtiu)
            def likedVacancies = CandidateVacancyDAO.findLikedVacanciesByCandidate(candidateId)
            likedVacancies.each { vId ->
                if (MatchService.isMatch(candidateId, vId, 'vacancy')) {
                    matches.add("MATCH: Candidato $candidateId e Vaga $vId")
                }
            }
        }
        
        // Verifica também match direto com a empresa
        if (MatchService.isMatch(candidateId, companyId, 'company')) {
             matches.add("MATCH: Candidato $candidateId e Empresa $companyId")
        }
        
        return matches
    }
}