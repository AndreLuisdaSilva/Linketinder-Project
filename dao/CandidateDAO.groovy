package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.ConnectionManager
import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
import groovy.sql.Sql
import java.sql.Connection
import java.util.Optional

class CandidateDAO {

    boolean save(Candidato candidate) {
        String query = """
            INSERT INTO candidates
            (name, email, description, education, experience, cep, age, password, country, state, skills)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            def result = sql.withTransaction {
                sql.executeUpdate(query, [
                    candidate.name, candidate.email, candidate.description,
                    candidate.education, candidate.experience, candidate.cep,
                    candidate.age, candidate.password, candidate.country, candidate.state, candidate.skills.join(',')
                ])
            }
            return result > 0
        } finally {
            conn.close()
        }
    }

    Optional<Candidato> findById(Long id) {
        String query = "SELECT * FROM candidates WHERE id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            def row = sql.firstRow(query, [id])
            return Optional.ofNullable(row).map {
                new Candidato(
                    id: it.id,
                    name: it.name,
                    email: it.email,
                    description: it.description,
                    education: it.education,
                    experience: it.experience,
                    cep: it.cep,
                    age: it.age,
                    password: it.password,
                    country: it.country,
                    state: it.state,
                    skills: it.skills?.split(',') ?: []
                )
            }
        } finally {
            conn.close()
        }
    }

    List<Candidato> findAll() {
        String query = "SELECT * FROM candidates ORDER BY id"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            return sql.rows(query).collect { row ->
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
        } finally {
            conn.close()
        }
    }

    boolean update(Candidato candidate) {
        String query = """
            UPDATE candidates
            SET name = ?, email = ?, description = ?, education = ?,
                experience = ?, cep = ?, age = ?, password = ?, country = ?, state = ?, skills = ?
            WHERE id = ?
        """
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            def result = sql.withTransaction {
                sql.executeUpdate(query, [
                    candidate.name, candidate.email, candidate.description,
                    candidate.education, candidate.experience, candidate.cep,
                    candidate.age, candidate.password, candidate.country, candidate.state,
                    candidate.skills.join(','),
                    candidate.id
                ])
            }
            return result > 0
        } finally {
            conn.close()
        }
    }

    boolean delete(Long id) {
        String query = "DELETE FROM candidates WHERE id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            def result = sql.withTransaction {
                sql.executeUpdate(query, [id])
            }
            return result > 0
        } finally {
            conn.close()
        }
    }
}
