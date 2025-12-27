package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.ConnectionManager
import groovy.sql.Sql
import groovy.sql.GroovyRowResult
import java.sql.Connection

class CandidateCompanyDAO {

    static void like(int candidateId, int companyId) {
        String query = """
            INSERT INTO candidate_companies (candidate_id, company_id, liked) 
            VALUES (?, ?, ?) 
            ON CONFLICT (candidate_id, company_id) DO NOTHING
        """
        
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            sql.execute(query, [candidateId, companyId, true])
        } finally {
            conn.close()
        }
    }

    static void dislike(int candidateId, int companyId) {
        String query = "DELETE FROM candidate_companies WHERE candidate_id = ? AND company_id = ?"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            sql.execute(query, [candidateId, companyId])
        } finally {
            conn.close()
        }
    }

    static boolean hasLiked(int candidateId, int companyId) {
        String query = "SELECT COUNT(*) AS count FROM candidate_companies WHERE candidate_id = ? AND company_id = ? AND liked = true"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            GroovyRowResult row = sql.firstRow(query, [candidateId, companyId])
            return (row.count as Long) > 0
        } finally {
            conn.close()
        }
    }

    // Métodos auxiliares de busca...
    static List<Integer> findLikedCompaniesByCandidate(int candidateId) {
        String query = "SELECT company_id FROM candidate_companies WHERE candidate_id = ? AND liked = true"
        Connection conn = ConnectionManager.getInstance().getConnection()
        try {
            Sql sql = new Sql(conn)
            return sql.rows(query, [candidateId]).collect { it.company_id as Integer }
        } finally {
            conn.close()
        }
    }
}