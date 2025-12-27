package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.ConnectionManager
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
import groovy.sql.Sql
import java.sql.Connection

class CompanyDAO {

    static void save(Empresa company) {
        String query = """INSERT INTO companies (name, email, description, cnpj, country, cep, password, state, skills)
                     VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (email) DO NOTHING"""
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [
                company.name, company.email, company.description,
                company.cnpj, company.country, company.cep, company.password, company.state, company.skills.join(',')
            ])
        }
        conn.close()
    }

    static Empresa findById(Long id) {
        String query = "SELECT * FROM companies WHERE id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        def result = sql.firstRow(query, [id])
        conn.close()
        if (result) {
            return new Empresa(
                id: result.id, name: result.name, email: result.email,
                description: result.description, cnpj: result.cnpj,
                country: result.country, cep: result.cep, password: result.password,
                state: result.state, skills: result.skills?.split(',') ?: []
            )
        }
        return null
    }

    static List<Empresa> findAll() {
        String query = "SELECT * FROM companies"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        List<Empresa> results = sql.rows(query).collect { row ->
            new Empresa(
                id: row.id, name: row.name, email: row.email,
                description: row.description, cnpj: row.cnpj,
                country: row.country, cep: row.cep, password: row.password,
                state: row.state, skills: row.skills?.split(',') ?: []
            )
        }
        conn.close()
        return results
    }

    static void update(Empresa company) {
        String query = "UPDATE companies SET name = ?, email = ?, description = ?, cnpj = ?, country = ?, cep = ?, password = ?, state = ?, skills = ? WHERE id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [
                company.name, company.email, company.description,
                company.cnpj, company.country, company.cep,
                company.password, company.state,  (company.skills ?: []).join(','), company.id
            ])
        }
        conn.close()
    }

    static void delete(Long id) {
        String query = "DELETE FROM companies WHERE id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [id])
        }
        conn.close()
    }
}
