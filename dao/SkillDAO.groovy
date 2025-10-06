package com.example.demo.exerciciosgroovy.Linketinder.dao

import com.example.demo.exerciciosgroovy.Linketinder.db.Database
import com.example.demo.exerciciosgroovy.Linketinder.model.Skill
import groovy.sql.Sql

class SkillDAO {

    static void save(Skill skill) {
        def query = "INSERT INTO skills (name) VALUES (?) ON CONFLICT (name) DO NOTHING"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [skill.name])
        }
        conn.close()
    }

    static Skill findById(int id) {
        def query = "SELECT * FROM skills WHERE id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def result = sql.firstRow(query, [id])
        conn.close()
        if (result) {
            return new Skill(id: result.id, name: result.name)
        }
        return null
    }

    static List<Skill> findAll() {
        def query = "SELECT * FROM skills"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        def results = sql.rows(query).collect { row ->
            new Skill(id: row.id, name: row.name)
        }
        conn.close()
        return results
    }

    static void update(Skill skill) {
        def query = "UPDATE skills SET name = ? WHERE id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [skill.name, skill.id])
        }
        conn.close()
    }

    static void delete(int id) {
        def query = "DELETE FROM skills WHERE id = ?"
        def conn = Database.getConnection()
        def sql = new Sql(conn)
        sql.withTransaction {
            sql.execute(query, [id])
        }
        conn.close()
    }
}
