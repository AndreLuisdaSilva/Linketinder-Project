package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.Database
import groovy.sql.Sql

class CandidateVacancyDAO {

    static void like(int candidateId, int vacancyId) {
        def query = "INSERT INTO candidate_vacancies (candidate_id, vacancy_id, liked) VALUES (?, ?, ?)"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [candidateId, vacancyId, true])
        }
        conn.close()
    }

    static void dislike(int candidateId, int vacancyId) {
        def query = "DELETE FROM candidate_vacancies WHERE candidate_id = ? AND vacancy_id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [candidateId, vacancyId])
        }
        conn.close()
    }

    static boolean hasLiked(int candidateId, int vacancyId) {
        def query = "SELECT COUNT(*) AS count FROM candidate_vacancies WHERE candidate_id = ? AND vacancy_id = ? AND liked = true"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def result = sql.firstRow(query, [candidateId, vacancyId])
        conn.close()
        return result.count > 0
    }

    static List<Integer> findLikedVacanciesByCandidate(int candidateId) {
        def query = "SELECT vacancy_id FROM candidate_vacancies WHERE candidate_id = ? AND liked = true"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def results = sql.rows(query, [candidateId]).collect { it.vacancy_id }
        conn.close()
        return results
    }

    static List<Integer> findLikedCandidatesByVacancy(int vacancyId) {
        def query = "SELECT candidate_id FROM candidate_vacancies WHERE vacancy_id = ? AND liked = true"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def results = sql.rows(query, [vacancyId]).collect { it.candidate_id }
        conn.close()
        return results
    }
}
