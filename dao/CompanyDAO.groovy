package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.Database
import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
import groovy.sql.Sql

class CompanyDAO {

    static void save(Empresa company) {
        def query = """INSERT INTO companies (name, email, description, cnpj, country, cep, password)
                     VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT (email) DO NOTHING"""
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [
                company.name, company.email, company.description,
                company.cnpj, company.country, company.cep, company.password
            ])
        }
        conn.close()
    }

    static Empresa findById(int id) {
        def query = "SELECT * FROM companies WHERE id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def result = sql.firstRow(query, [id])
        conn.close()
        if (result) {
            return new Empresa(
                id: result.id, name: result.name, email: result.email,
                description: result.description, cnpj: result.cnpj,
                country: result.country, cep: result.cep, password: result.password
            )
        }
        return null
    }

    static List<Empresa> findAll() {
        def query = "SELECT * FROM companies"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def results = sql.rows(query).collect { row ->
            new Empresa(
                id: row.id, name: row.name, email: row.email,
                description: row.description, cnpj: row.cnpj,
                country: row.country, cep: row.cep, password: row.password
            )
        }
        conn.close()
        return results
    }

    static void update(Empresa company) {
        def query = "UPDATE companies SET name = ?, email = ?, description = ?, cnpj = ?, country = ?, cep = ?, password = ? WHERE id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [
                company.name, company.email, company.description,
                company.cnpj, company.country, company.cep,
                company.password, company.id
            ])
        }
        conn.close()
    }

    static void delete(int id) {
        def query = "DELETE FROM companies WHERE id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [id])
        }
        conn.close()
    }
}
