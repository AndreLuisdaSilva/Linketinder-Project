package com.example.demo.exerciciosgroovy.Linketinder.service

import com.example.demo.exerciciosgroovy.Linketinder.dao.CandidateDAO
import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato

class CandidateService {
    private CandidateDAO candidateDAO

    CandidateService(CandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO
    }

    Candidato registerCandidate(Map candidateData) {
        def candidato = new Candidato(candidateData)
        candidateDAO.save(candidato)
        return candidato
    }

    List<Candidato> getAllCandidates() {
        return candidateDAO.findAll()
    }
}
