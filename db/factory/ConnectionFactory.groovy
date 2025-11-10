package com.example.demo.exerciciosgroovy.Linketinder.db.factory

import java.sql.Connection

interface ConnectionFactory {
    Connection getConnection()
}
