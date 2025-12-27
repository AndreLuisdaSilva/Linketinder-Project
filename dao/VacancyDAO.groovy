package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.ConnectionManager
import com.example.demo.exerciciosgroovy.Linketinder.model.Vacancy
import groovy.sql.Sql
import java.sql.Connection
import java.util.Optional

class VacancyDAO {

    static boolean save(Vacancy vacancy) {
        String query = "INSERT INTO vacancies (title, description, company_id, location) VALUES (?, ?, ?, ?)"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            def result = sql.withTransaction {
                sql.execute(query, [vacancy.title, vacancy.description, vacancy.companyId, vacancy.location])
            }
            return result > 0
        } finally {
            conn.close()
        }
    }

    static Optional<Vacancy> findById(Long id) {
        String query = "SELECT * FROM vacancies WHERE id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            def row = sql.firstRow(query, [id])
            return Optional.ofNullable(row).map {
                new Vacancy(
                    id: it.id, title: it.title,
                    description: it.description,
                    companyId: it.company_id, location: it.location
                )
            }
        } finally {
            conn.close()
        }
    }

    static List<Vacancy> findByCompanyId(Long companyId) {
        String query = "SELECT * FROM vacancies WHERE company_id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            return sql.rows(query, [companyId]).collect { row ->
                new Vacancy(
                    id: row.id, title: row.title,
                    description: row.description,
                    companyId: row.company_id, location: row.location
                )
            }
        } finally {
            conn.close()
        }
    }

    static List<Vacancy> findAll() {
        String query = "SELECT * FROM vacancies"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            List<Vacancy> results = sql.rows(query).collect { row ->
                new Vacancy(
                    id: row.id, title: row.title,
                    description: row.description,
                    companyId: row.company_id, location: row.location
                )
            }
            return results
        } finally {
            conn.close()
        }
    }

    static boolean update(Vacancy vacancy) {
        String query = "UPDATE vacancies SET title = ?, description = ?, company_id = ?, location = ? WHERE id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            boolean result = sql.withTransaction {
                sql.execute(query, [vacancy.title, vacancy.description, vacancy.companyId, vacancy.location, vacancy.id])
            }
            return result > 0
        } finally {
            conn.close()
        }
    }

    static boolean delete(Long id) {
        String query = "DELETE FROM vacancies WHERE id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            boolean result = sql.withTransaction {
                sql.execute(query, [id])
            }
            return result > 0
        } finally {
            conn.close()
        }
    }
}
