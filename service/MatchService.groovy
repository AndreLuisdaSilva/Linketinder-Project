package com.example.demo.exerciciosgroovy.Linketinder.service

import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateCompanyDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateSkillDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateVacancyDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.CompanyCandidateDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.VacancyDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.VacancySkillDAO
import com.example.demo.exerciciosgroovy.Linketinder.model.Skill
import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy
import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateDAO

import java.util.Optional

class MatchService {

    private static VacancyDAO vacancyDAO = new VacancyDAO()
    private VacancySkillDAO vacancySkillDAO = new VacancySkillDAO()
    private CandidateSkillDAO candidateSkillDAO = new CandidateSkillDAO()
    private CandidateDAO candidateDAO = new CandidateDAO()


    static boolean isMatch(int candidateId, int entityId, String entityType) {
        if (entityType == 'vacancy') {
            Optional<Vacancy> optionalVacancy = VacancyDAO.findById(entityId as Long)
            
            if (optionalVacancy.isEmpty()) return false

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

    List<Map> getVacanciesWithAffinity(Long candidateId) {
        List<Skill> candidateSkills = CandidateSkillDAO.findSkillsByCandidateId(candidateId.toInteger())
        
        List<String> candidateSkillNames = candidateSkills.collect { it.name.trim().toLowerCase() }

        List<Vacancy> allVacancies = vacancyDAO.findAll()
        List<Map> result = []

        for (Vacancy vacancy : allVacancies) {
            List<Skill> vacancySkills = VacancySkillDAO.findSkillsByVacancyId(vacancy.id.toInteger())
            
            List<String> vacancySkillNames = vacancySkills.collect { it.name.trim().toLowerCase() }

            double affinity = calculateAffinity(candidateSkillNames, vacancySkillNames)

            result.add([
                id: vacancy.id,
                companyId: vacancy.companyId,
                title: vacancy.title,
                description: vacancy.description,
                location: vacancy.location,
                requiredSkills: vacancySkills.collect { it.name },
                affinity: Math.round(affinity)
            ])
        }

        result.sort { -it.affinity }
        return result
    }

    List<Map> getCandidatesWithAffinityForVacancy(Long vacancyId) {
        List<Skill> vacancySkills = VacancySkillDAO.findSkillsByVacancyId(vacancyId.toInteger())
        List<Integer> vacancySkillIds = vacancySkills.collect { it.id.toInteger() }

        List<Candidato> allCandidates = candidateDAO.findAll()
        List<Map> result = []

        for (Candidato candidate : allCandidates) {
            List<Skill> candidateSkills = CandidateSkillDAO.findSkillsByCandidateId(candidate.id.toInteger())
            List<Integer> candidateSkillIds = candidateSkills.collect { it.id.toInteger() }

            double affinity = calculateAffinity(candidateSkillIds, vacancySkillIds)

            result.add([
                id: candidate.id,
                name: candidate.name,
                email: candidate.email,
                state: candidate.state,
                skills: candidateSkills.collect { it.name },
                affinity: Math.round(affinity)
            ])
        }

        result.sort { -it.affinity }
        return result
    }

    private double calculateAffinity(List<String> candidateSkills, List<String> requiredSkills) {
        if (requiredSkills.isEmpty()) {
            return 0.0 
        }
        
        def commonSkills = candidateSkills.intersect(requiredSkills)
        
        return (commonSkills.size() / requiredSkills.size()) * 100.0
    }
}