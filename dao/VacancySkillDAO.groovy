package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.Database
import com.example.demo.exerciciosgroovy.Linketinder.model.Skill
import groovy.sql.Sql

class VacancySkillDAO {

    static void addSkillToVacancy(int vacancyId, int skillId) {
        def query = "INSERT INTO vacancy_skills (vacancy_id, skill_id) VALUES (?, ?) ON CONFLICT (vacancy_id, skill_id) DO NOTHING"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [vacancyId, skillId])
        }
        conn.close()
    }

    static void removeSkillFromVacancy(int vacancyId, int skillId) {
        def query = "DELETE FROM vacancy_skills WHERE vacancy_id = ? AND skill_id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [vacancyId, skillId])
        }
        conn.close()
    }

    static List<Skill> findSkillsByVacancyId(int vacancyId) {
        def query = """
            SELECT s.id, s.name
            FROM skills s
            JOIN vacancy_skills vs ON s.id = vs.skill_id
            WHERE vs.vacancy_id = ?
        """
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def results = sql.rows(query, [vacancyId]).collect { row ->
            new Skill(id: row.id, name: row.name)
        }
        conn.close()
        return results
    }
}
