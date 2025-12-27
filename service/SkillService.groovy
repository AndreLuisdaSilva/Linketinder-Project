package com.example.demo.exerciciosgroovy.Linketinder.service

import com.example.demo.exerciciosgroovy.Linketinder.dao.SkillDAO
import com.example.demo.exerciciosgroovy.Linketinder.model.Skill

class SkillService {

    private SkillDAO skillDAO = new SkillDAO()

    List<Skill> getAllSkills() {
        return skillDAO.findAll()
    }

    Skill findSkillById(Long id) {
        if (id == null) return null
        return skillDAO.findById(id.toInteger())
    }

    Skill createSkill(Map skillData) {
        if (!skillData.name) {
            throw new IllegalArgumentException("O nome da competência é obrigatório.")
        }
        
        Skill newSkill = new Skill(name: skillData.name as String)

        int generatedId = skillDAO.save(newSkill)
        
        if (generatedId > 0) {
            newSkill.id = generatedId.toLong() 
            return newSkill
        } else {
            throw new RuntimeException("Erro ao criar competência ou competência já existe.")
        }
    }

    Skill updateSkill(Long id, Map skillData) {
        Skill existingSkill = skillDAO.findById(id.toInteger())
        
        if (existingSkill) {
            if (skillData.name) {
                existingSkill.name = skillData.name as String
            }
            skillDAO.update(existingSkill)
            return existingSkill
        }
        return null
    }

    boolean deleteSkill(Long id) {
        skillDAO.delete(id.toInteger())
        return true
    }
}