package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.ConnectionManager
import com.example.demo.exerciciosgroovy.Linketinder.model.Skill
import groovy.sql.Sql

class CandidateSkillDAO {

    static void addSkillToCandidate(int candidateId, int skillId) {
        def query = "INSERT INTO candidate_skills (candidate_id, skill_id) VALUES (?, ?) ON CONFLICT (candidate_id, skill_id) DO NOTHING"
        def conn = ConnectionManager.getInstance().getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [candidateId, skillId])
        }
        conn.close()
    }

    static void removeSkillFromCandidate(int candidateId, int skillId) {
        def query = "DELETE FROM candidate_skills WHERE candidate_id = ? AND skill_id = ?"
        def conn = ConnectionManager.getInstance().getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [candidateId, skillId])
        }
        conn.close()
    }

    static List<Skill> findSkillsByCandidateId(int candidateId) {
        def query = """
            SELECT s.id, s.name
            FROM skills s
            JOIN candidate_skills cs ON s.id = cs.skill_id
            WHERE cs.candidate_id = ?
        """
        def conn = ConnectionManager.getInstance().getConnection()
        def sql = new Sql(conn)
        def results = sql.rows(query, [candidateId]).collect { row ->
            new Skill(id: row.id, name: row.name)
        }
        conn.close()
        return results
    }
}
