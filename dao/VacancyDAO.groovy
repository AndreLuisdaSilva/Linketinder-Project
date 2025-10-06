package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.Database
import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy
import groovy.sql.Sql

class VacancyDAO {

    static void save(Vacancy vacancy) {
        def query = "INSERT INTO vacancies (title, description, company_id, location) VALUES (?, ?, ?, ?)"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [vacancy.title, vacancy.description, vacancy.companyId, vacancy.location])
        }
        conn.close()
    }

    static Vacancy findById(int id) {
        def query = "SELECT * FROM vacancies WHERE id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def result = sql.firstRow(query, [id])
        conn.close()
        if (result) {
            return new Vacancy(
                id: result.id, title: result.title,
                description: result.description,
                companyId: result.company_id, location: result.location
            )
        }
        return null
    }

    static List<Vacancy> findAll() {
        def query = "SELECT * FROM vacancies"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def results = sql.rows(query).collect { row ->
            new Vacancy(
                id: row.id, title: row.title,
                description: row.description,
                companyId: row.company_id, location: row.location
            )
        }
        conn.close()
        return results
    }

    static void update(Vacancy vacancy) {
        def query = "UPDATE vacancies SET title = ?, description = ?, company_id = ?, location = ? WHERE id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [vacancy.title, vacancy.description, vacancy.companyId, vacancy.location, vacancy.id])
        }
        conn.close()
    }

    static void delete(int id) {
        def query = "DELETE FROM vacancies WHERE id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [id])
        }
        conn.close()
    }
}
