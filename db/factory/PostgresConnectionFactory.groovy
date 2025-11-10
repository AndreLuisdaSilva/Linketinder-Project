package com.example.demo.exerciciosgroovy.Linketinder.db.factory

import java.sql.Connection
import java.sql.DriverManager

class PostgresConnectionFactory implements ConnectionFactory {
    @Override
    Connection getConnection() {
        try{
            Class.forName("org.postgresql.Driver")
            def dbUrl = "jdbc:postgresql://localhost:5432/linketinder"
            return DriverManager.getConnection(dbUrl, "linketinder_user", "linketinder_password")
        } 
        catch (Exception e) {
            throw new RuntimeException("Erro ao criar conexão PostgreSql", e);
        }
      
    }
}
