package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.service.VacancyService
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "VacancyServlet", urlPatterns = "/vagas/*")
class VacancyServlet extends HttpServlet {

    private VacancyService vacancyService = new VacancyService()
    private JsonSlurper jsonSlurper = new JsonSlurper()

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        // GET /vagas -> Listar todas
        if (pathInfo == null || pathInfo == "/") {
            def vacancies = vacancyService.getAllVacancies()
            resp.writer.write(JsonOutput.toJson(vacancies))
            return
        }

        String[] parts = pathInfo.split("/")

        try {
            Long id = parts[1].toLong()

            //  GET /vagas/{id}/skills
            if (parts.length > 2 && "skills".equals(parts[2])) {
                def skills = vacancyService.getSkillsByVacancyId(id)
                resp.writer.write(JsonOutput.toJson(skills))
            } 
            //  GET /vagas/{id}
            else {
                def vacancy = vacancyService.findVacancyById(id)
                if (vacancy) {
                    resp.writer.write(JsonOutput.toJson(vacancy))
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Vaga não encontrada")
                }
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido")
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        // POST /vagas -> Cadastrar nova (Lógica Antiga)
        if (pathInfo == null || pathInfo == "/") {
            try {
                def dadosVaga = jsonSlurper.parse(req.reader) as Map
                vacancyService.registerVacancy(dadosVaga)
                resp.setStatus(HttpServletResponse.SC_CREATED)
                resp.writer.write(JsonOutput.toJson([message: "Vaga cadastrada com sucesso!"]))
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao criar vaga: ${e.message}")
            }
            return
        }

        // /vagas/{id}/skills -> Associar competência
        // URL: .../vagas/1/skills | Body: { "skillId": 5 }
        String[] parts = pathInfo.split("/")
        if (parts.length > 2 && "skills".equals(parts[2])) {
            try {
                Long vacancyId = parts[1].toLong()
                def body = jsonSlurper.parse(req.reader) as Map
                Long skillId = body.skillId?.toLong()

                if (skillId) {
                    vacancyService.addSkillToVacancy(vacancyId, skillId)
                    resp.setStatus(HttpServletResponse.SC_CREATED)
                    resp.writer.write(JsonOutput.toJson([message: "Competência adicionada à vaga com sucesso"]))
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "skillId obrigatório")
                }
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao associar skill: ${e.message}")
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        if (pathInfo == null || pathInfo == "/") {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID necessário")
            return
        }

        try {
            Long id = pathInfo.substring(1).toLong()
            def novosDados = jsonSlurper.parse(req.reader) as Map
            def vagaAtualizada = vacancyService.updateVacancy(id, novosDados)

            if (vagaAtualizada) {
                resp.writer.write(JsonOutput.toJson(vagaAtualizada))
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Vaga não encontrada")
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido")
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro: ${e.message}")
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        if (pathInfo == null || pathInfo == "/") {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID necessário")
            return
        }
        
        String[] parts = pathInfo.split("/")
        
        try {
            Long id = parts[1].toLong()

            // LÓGICA NOVA: DELETE /vagas/{id}/skills -> Remover skill
            // Body: { "skillId": 5 }
            if (parts.length > 2 && "skills".equals(parts[2])) {
                try {
                    def body = jsonSlurper.parse(req.reader) as Map
                    Long skillId = body.skillId?.toLong()
                    
                    if (skillId) {
                        vacancyService.removeSkillFromVacancy(id, skillId)
                        resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
                    } else {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "skillId necessário")
                    }
                } catch (Exception e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao remover skill: ${e.message}")
                }
                return
            }

            // LÓGICA ANTIGA: DELETE /vagas/{id} -> Excluir Vaga
            if (vacancyService.deleteVacancy(id)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Vaga não encontrada")
            }

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido")
        }
    }
}