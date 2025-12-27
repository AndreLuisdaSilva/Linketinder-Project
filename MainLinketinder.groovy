package com.example.demo.exerciciosgroovy.Linketinder

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder

import com.example.demo.exerciciosgroovy.Linketinder.controller.CandidatoServlet
import com.example.demo.exerciciosgroovy.Linketinder.controller.CompanyServlet
import com.example.demo.exerciciosgroovy.Linketinder.controller.VacancyServlet
import com.example.demo.exerciciosgroovy.Linketinder.controller.LikeServlet
import com.example.demo.exerciciosgroovy.Linketinder.controller.SkillServlet

class MainLinketinder {
    static void main(String[] args) {
        
        def server = new Server(8080)

        def context = new ServletContextHandler(ServletContextHandler.SESSIONS)
        context.contextPath = "/"

        context.addServlet(new ServletHolder(new CandidatoServlet()), "/candidatos/*")
        context.addServlet(new ServletHolder(new CompanyServlet()), "/empresas/*")
        context.addServlet(new ServletHolder(new VacancyServlet()), "/vagas/*")
        context.addServlet(new ServletHolder(new LikeServlet()), "/likes/*")
        context.addServlet(new ServletHolder(new SkillServlet()), "/skills/*")

        server.handler = context
        server.start()
        server.join()
    }
}