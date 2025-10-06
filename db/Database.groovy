package com.example.demo.exerciciosgroovy.Linketinder.db

import java.sql.Connection
import java.sql.DriverManager

class Database {
    static Connection getConnection() {
        Class.forName("org.postgresql.Driver")
        def dbUrl = "jdbc:postgresql://localhost:5432/linketinder_db"
        return DriverManager.getConnection(dbUrl, "linketinder_user", "linketinder_password")
    }
}
