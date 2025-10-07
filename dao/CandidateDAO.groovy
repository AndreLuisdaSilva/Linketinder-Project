
package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.Database
import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import groovy.sql.Sql

class CandidateDAO {

    static void save(Candidato candidate) {
        def query = """
            INSERT INTO candidates 
            (name, email, description, education, experience, cep, age, password, country, state, skills) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [
                candidate.name, candidate.email, candidate.description,
                candidate.education, candidate.experience, candidate.cep,
                candidate.age, candidate.password, candidate.country, candidate.state, candidate.skills.join(',')
            ])
        }
        conn.close()
    }

    static Candidato findById(int id) {
        def query = "SELECT * FROM candidates WHERE id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def result = sql.firstRow(query, [id])
        conn.close()
        if (result) {
            return new Candidato(
                id: result.id,
                name: result.name,
                email: result.email,
                description: result.description,
                education: result.education,
                experience: result.experience,
                cep: result.cep,
                age: result.age,
                password: result.password,
                country: result.country,
                state: result.state,
                skills: result.skills?.split(',') ?: []
            )
        }
        return null
    }

    static List<Candidato> findAll() {
        def query = "SELECT * FROM candidates"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def results = sql.rows(query).collect { row ->
            new Candidato(
                id: row.id,
                name: row.name,
                email: row.email,
                description: row.description,
                education: row.education,
                experience: row.experience,
                cep: row.cep,
                age: row.age,
                password: row.password,
                country: row.country,
                state: row.state,
                skills: row.skills?.split(',') ?: []
            )
        }
        conn.close()
        return results
    }

    static void update(Candidato candidate) {
        def query = """
            UPDATE candidates 
            SET name = ?, email = ?, description = ?, education = ?, 
                experience = ?, cep = ?, age = ?, password = ?, country = ?, state = ?, skills = ? 
            WHERE id = ?
        """
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [
                candidate.name, candidate.email, candidate.description,
                candidate.education, candidate.experience, candidate.cep,
                candidate.age, candidate.password, candidate.country, candidate.state, candidate.skills.join(','), candidate.id
            ])
        }
        conn.close()
    }

    static void delete(int id) {
        def query = "DELETE FROM candidates WHERE id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [id])
        }
        conn.close()
    }
}
