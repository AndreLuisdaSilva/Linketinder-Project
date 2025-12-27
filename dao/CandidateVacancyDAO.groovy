package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.ConnectionManager
import groovy.sql.Sql
import groovy.sql.GroovyRowResult
import java.sql.Connection

class CandidateVacancyDAO {

    static void like(int candidateId, int vacancyId) {
        String query = """
            INSERT INTO candidate_vacancies (candidate_id, vacancy_id, liked) 
            VALUES (?, ?, ?) 
            ON CONFLICT (candidate_id, vacancy_id) DO NOTHING
        """
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [candidateId, vacancyId, true])
        }
        conn.close()
    }

    static void dislike(int candidateId, int vacancyId) {
        String query = "DELETE FROM candidate_vacancies WHERE candidate_id = ? AND vacancy_id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [candidateId, vacancyId])
        }
        conn.close()
    }

    static boolean hasLiked(int candidateId, int vacancyId) {
        String query = "SELECT COUNT(*) AS count FROM candidate_vacancies WHERE candidate_id = ? AND vacancy_id = ? AND liked = true"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        GroovyRowResult row = sql.firstRow(query, [candidateId, vacancyId])
        conn.close()
        long count = row.count as Long
        return count > 0
    }

    static List<Integer> findLikedVacanciesByCandidate(int candidateId) {
        String query = "SELECT vacancy_id FROM candidate_vacancies WHERE candidate_id = ? AND liked = true"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        List<GroovyRowResult> rows = sql.rows(query, [candidateId])
        conn.close()
        return rows.collect { it.vacancy_id as Integer }
    }

    static List<Integer> findLikedCandidatesByVacancy(int vacancyId) {
        String query = "SELECT candidate_id FROM candidate_vacancies WHERE vacancy_id = ? AND liked = true"
        Connection conn = ConnectionManager.getInstance().getConnection()
        Sql sql = new Sql(conn)
        List<GroovyRowResult> rows = sql.rows(query, [vacancyId])
        conn.close()
        return rows.collect { it.candidate_id as Integer }
    }
}
