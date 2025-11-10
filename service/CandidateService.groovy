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

    Candidato updateCandidate(Long id, Map novosDados) {
        Candidato candidatoExistente = findCandidateById(id) 

        if (candidatoExistente) {
            
            candidatoExistente.name = novosDados.name ?: candidatoExistente.name
            candidatoExistente.email = novosDados.email ?: candidatoExistente.email
            candidatoExistente.age = novosDados.age ?: candidatoExistente.age
            candidatoExistente.state = novosDados.state ?: candidatoExistente.state
            candidatoExistente.cep = novosDados.cep ?: candidatoExistente.cep
            candidatoExistente.skills = novosDados.skills ?: candidatoExistente.skills
            candidatoExistente.description = novosDados.personalDescription ?: candidatoExistente.description

            candidateDAO.update(candidatoExistente)
            return candidatoExistente

        } else {
            return null
        }
    }

    List<Candidato> getAllCandidates() {
        return candidateDAO.findAll()
    }

    Candidato findCandidateById(Long id) {
        return candidateDAO.findById(id)
    }
}
