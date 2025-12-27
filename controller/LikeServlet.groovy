package com.example.demo.exerciciosgroovy.Linketinder.controller

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class LikeServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String pathInfo = req.getPathInfo()
            List<String> matchResults = []
            String actionMessage = ""

            if (pathInfo == "/candidate-likes-vacancy") {
                int candidateId = req.getParameter("candidateId") as int
                int vacancyId = req.getParameter("vacancyId") as int
                matchResults = LikeController.candidateLikesVacancy(candidateId, vacancyId)
                actionMessage = "Candidato $candidateId curtiu a Vaga $vacancyId"

            } else if (pathInfo == "/candidate-likes-company") {
                int candidateId = req.getParameter("candidateId") as int
                int companyId = req.getParameter("companyId") as int
                matchResults = LikeController.candidateLikesCompany(candidateId, companyId)
                actionMessage = "Candidato $candidateId curtiu a Empresa $companyId"

            } else if (pathInfo == "/company-likes-candidate") {
                int companyId = req.getParameter("companyId") as int
                int candidateId = req.getParameter("candidateId") as int
                
                String vacancyParam = req.getParameter("vacancyId")
                Integer vacancyId = vacancyParam ? vacancyParam as Integer : null

                matchResults = LikeController.companyLikesCandidate(companyId, candidateId, vacancyId)
                
                if (vacancyId) {
                    actionMessage = "Empresa $companyId curtiu o Candidato $candidateId para a Vaga $vacancyId"
                } else {
                    actionMessage = "Empresa $companyId curtiu o Candidato $candidateId"
                }
            }

            resp.setStatus(HttpServletResponse.SC_OK)
            resp.setContentType("application/json")
            resp.setCharacterEncoding("UTF-8")
            
            if (matchResults.isEmpty()) {
                resp.writer.write("{ \"message\": \"$actionMessage\", \"match\": false, \"details\": [] }")
            } else {
                String jsonMatches = matchResults.collect { "\"$it\"" }.join(", ")
                resp.writer.write("{ \"message\": \"$actionMessage\", \"match\": true, \"details\": [$jsonMatches] }")
            }

        } catch (Exception e) {
             e.printStackTrace()
             resp.sendError(500, e.message)
        }
    }
}