package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.ConnectionManager
import com.example.demo.exerciciosgroovy.Linketinder.model.Skill
import groovy.sql.Sql
import java.sql.Connection

class SkillDAO {

    static int save(Skill skill) {
        String query = "INSERT INTO skills (name) VALUES (?)"
        
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        
        try {
            def keys = sql.executeInsert(query, [skill.name])
            if (keys) {
                return keys[0][0] as int
            }
            return 0
        } catch (Exception e) {
            e.printStackTrace()
            return -1 
        } finally {
            conn.close()
        }
    }
    
    static Skill findById(int id) {
        String query = "SELECT * FROM skills WHERE id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        def result = sql.firstRow(query, [id])
        conn.close()
        if (result) return new Skill(id: result.id, name: result.name)
        return null
    }

    static List<Skill> findAll() {
        String query = "SELECT * FROM skills"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        List<Skill> results = sql.rows(query).collect { new Skill(id: it.id, name: it.name) }
        conn.close()
        return results
    }

    static void update(Skill skill) {
        String query = "UPDATE skills SET name = ? WHERE id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        sql.execute(query, [skill.name, skill.id])
        conn.close()
    }

    static void delete(int id) {
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        sql.withTransaction {
            sql.execute("DELETE FROM candidate_skills WHERE skill_id = ?", [id])
            sql.execute("DELETE FROM vacancy_skills WHERE skill_id = ?", [id])
            sql.execute("DELETE FROM skills WHERE id = ?", [id])
        }
        conn.close()
    }
}