package com.example.demo.exerciciosgroovy.Linketinder
import com.example.demo.exerciciosgroovy.Linketinder.controller.SistemaController
import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

class Main {
    static void main(String[] args) {
        //André Luis da Silva
        def menuView = new Menu()
        def controller = new SistemaController(menuView)
        controller.iniciar()
    }
}