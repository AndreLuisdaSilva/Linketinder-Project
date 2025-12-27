package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.ConnectionManager
import com.example.demo.exerciciosgroovy.Linketinder.model.Skill
import groovy.sql.Sql
import java.sql.Connection

class VacancySkillDAO {

    static void addSkillToVacancy(int vacancyId, int skillId) {
        String query = "INSERT INTO vacancy_skills (vacancy_id, skill_id) VALUES (?, ?) ON CONFLICT (vacancy_id, skill_id) DO NOTHING"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [vacancyId, skillId])
        }
        conn.close()
    }

    static void removeSkillFromVacancy(int vacancyId, int skillId) {
        String query = "DELETE FROM vacancy_skills WHERE vacancy_id = ? AND skill_id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [vacancyId, skillId])
        }
        conn.close()
    }

    static List<Skill> findSkillsByVacancyId(int vacancyId) {
        String query = """
            SELECT s.id, s.name
            FROM skills s
            JOIN vacancy_skills vs ON s.id = vs.skill_id
            WHERE vs.vacancy_id = ?
        """
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        List<Skill> results = sql.rows(query, [vacancyId]).collect { row ->
            new Skill(id: row.id, name: row.name)
        }
        conn.close()
        return results
    }
}
