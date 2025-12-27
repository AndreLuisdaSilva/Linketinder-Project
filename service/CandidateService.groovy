package com.example.demo.exerciciosgroovy.Linketinder.service

import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateDAO
import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateSkillDAO
import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import com.example.demo.exerciciosgroovy.Linketinder.model.Skill
import java.util.Optional

class CandidateService {
    private CandidateDAO candidateDAO = new CandidateDAO()

    Candidato registerCandidate(Map candidateData) {
        def candidato = new Candidato(candidateData)
        candidateDAO.save(candidato)
        return candidato
    }

    List<Candidato> getAllCandidates() {
        return candidateDAO.findAll()
    }

    Candidato findCandidateById(Long id) {
        Optional<Candidato> optional = candidateDAO.findById(id)
        if (optional.isPresent()) {
            Candidato cand = optional.get()
            cand.skills = getSkillsByCandidateId(id)
            return cand
        }
        return null
    }

    Candidato updateCandidate(Long id, Map novosDados) {
        Candidato candidato = findCandidateById(id)
        if (candidato) {
            candidato.name = novosDados.name ?: candidato.name
            candidato.email = novosDados.email ?: candidato.email
            candidato.age = novosDados.age ?: candidato.age
            candidato.state = novosDados.state ?: candidato.state
            candidato.cep = novosDados.cep ?: candidato.cep
            candidato.description = novosDados.personalDescription ?: candidato.description
            
            candidateDAO.update(candidato)
            return candidato
        }
        return null
    }

    boolean deleteCandidate(Long id) {
        return candidateDAO.delete(id)
    }

    void addSkillToCandidate(Long candidateId, Long skillId) {
        CandidateSkillDAO.addSkillToCandidate(candidateId.toInteger(), skillId.toInteger())
    }

    void removeSkillFromCandidate(Long candidateId, Long skillId) {
        CandidateSkillDAO.removeSkillFromCandidate(candidateId.toInteger(), skillId.toInteger())
    }

    List<Skill> getSkillsByCandidateId(Long candidateId) {
        return CandidateSkillDAO.findSkillsByCandidateId(candidateId.toInteger())
    }
}