package com.example.demo.exerciciosgroovy.Linketinder

import com.example.demo.exerciciosgroovy.Linketinder.dao.*
import com.example.demo.exerciciosgroovy.Linketinder.db.Database
import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
import com.example.demo.exerciciosgroovy.Linketinder.model.Skill
import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy
import com.example.demo.exerciciosgroovy.Linketinder.service.MatchService

class Main {

    static void main(String[] args) {
        def groovySkill = new Skill(name: 'Groovy')
        def javaSkill = new Skill(name: 'Java')
        def springSkill = new Skill(name: 'Spring Boot')
        SkillDAO.save(groovySkill)
        SkillDAO.save(javaSkill)
        SkillDAO.save(springSkill)

        // Criar empresa
        def company = new Empresa(name: 'Linketinder', email: 'contato@linketinder.com', description: 'A melhor plataforma de match para vagas de tecnologia.', cnpj: '12.345.678/0001-90', country: 'Brasil', cep: '12345-678', password: '123')
        CompanyDAO.save(company)

        // Criar vaga
        def vacancy = new Vacancy(title: 'Desenvolvedor Groovy', description: 'Vaga para desenvolvedor Groovy com experiência em Spring Boot.', companyId: 1, location: 'Remoto')
        VacancyDAO.save(vacancy)

        // Associar competências à vaga
        VacancySkillDAO.addSkillToVacancy(1, 1) // Groovy
        VacancySkillDAO.addSkillToVacancy(1, 3) // Spring Boot

        // Criar candidato
        def candidate = new Candidato(name: 'João da Silva', email: 'joao.silva@email.com', description: 'Desenvolvedor Groovy e Java.', education: 'Ciência da Computação', experience: '5 anos', cep: '54321-876', age: 30, password: '456', country: 'Brasil')
        CandidateDAO.save(candidate)

        // Associar competências ao candidato
        CandidateSkillDAO.addSkillToCandidate(1, 1) // Groovy
        CandidateSkillDAO.addSkillToCandidate(1, 2) // Java

        // Encontrar matches
        def matchedVacancies = MatchService.findMatches(1)

        println "Vagas compatíveis com o candidato João da Silva:"
        matchedVacancies.each {
            println "- ${it.title}"
        }
    }
}
