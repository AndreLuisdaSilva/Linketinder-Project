package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.ConnectionManager
import com.example.demo.exerciciosgroovy.Linketinder.model.Skill
import groovy.sql.Sql
import java.sql.Connection
import java.util.Optional

class CandidateSkillDAO {

    static void addSkillToCandidate(int candidateId, int skillId) {
        String query = "INSERT INTO candidate_skills (candidate_id, skill_id) VALUES (?, ?) ON CONFLICT (candidate_id, skill_id) DO NOTHING"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [candidateId, skillId])
        }
        conn.close()
    }

    static void removeSkillFromCandidate(int candidateId, int skillId) {
        String query = "DELETE FROM candidate_skills WHERE candidate_id = ? AND skill_id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [candidateId, skillId])
        }
        conn.close()
    }

    static List<Skill> findSkillsByCandidateId(int candidateId) {
        String query = """
            SELECT s.id, s.name
            FROM skills s
            JOIN candidate_skills cs ON s.id = cs.skill_id
            WHERE cs.candidate_id = ?
        """
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
             Sql sql = new Sql(conn)
             List<Skill> skills = sql.rows(query, [candidateId]).collect { row ->
                new Skill(id: row.id, name: row.name)
            }
            return skills
        }finally{
            conn.close()
        }
    }
}
