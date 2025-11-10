package com.example.demo.exerciciosgroovy.Linketinder.db

import com.example.demo.exerciciosgroovy.Linketinder.db.factory.ConnectionFactory
import com.example.demo.exerciciosgroovy.Linketinder.db.factory.PostgresConnectionFactory

import java.sql.Connection

class ConnectionManager {
    private static ConnectionManager instance
    private ConnectionFactory connectionFactory

    private ConnectionManager() {
        this.connectionFactory = new PostgresConnectionFactory()
    }

    static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager()
        }
        return instance
    }

    void setConnectionFactory(ConnectionFactory factory) {
        this.connectionFactory = factory
    }

    Connection getConnection() {
        return connectionFactory.getConnection()
    }
}
