package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.ConnectionManager
import groovy.sql.Sql
import groovy.sql.GroovyRowResult // Importante importar isso
import java.sql.Connection

class CompanyCandidateDAO {

    static void like(int companyId, int candidateId) {
        String query = "INSERT INTO company_candidates (company_id, candidate_id, liked) VALUES (?, ?, ?) ON CONFLICT(company_id, candidate_id) DO NOTHING"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            sql.execute(query, [companyId, candidateId, true])
        } finally {
            conn.close()
        }
    }

    static void dislike(int companyId, int candidateId) {
        String query = "DELETE FROM company_candidates WHERE company_id = ? AND candidate_id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            sql.execute(query, [companyId, candidateId])
        } finally {
            conn.close()
        }
    }

    static boolean hasLiked(int companyId, int candidateId) {
        String query = "SELECT COUNT(*) AS count FROM company_candidates WHERE company_id = ? AND candidate_id = ? AND liked = true"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            
            GroovyRowResult row = sql.firstRow(query, [companyId, candidateId])
            
            return (row.count as Long) > 0
        } finally {
            conn.close()
        }
    }

    static List<Integer> findLikedCandidatesByCompany(int companyId) {
        String query = "SELECT candidate_id FROM company_candidates WHERE company_id = ? AND liked = true"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            return sql.rows(query, [companyId]).collect { it.candidate_id as Integer }
        } finally {
            conn.close()
        }
    }

    static List<Integer> findLikingCompaniesByCandidate(int candidateId) {
        String query = "SELECT company_id FROM company_candidates WHERE candidate_id = ? AND liked = true"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            return sql.rows(query, [candidateId]).collect { it.company_id as Integer }
        } finally {
            conn.close()
        }
    }
}