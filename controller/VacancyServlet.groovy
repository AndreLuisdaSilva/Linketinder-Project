package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.service.VacancyService
import com.example.demo.exerciciosgroovy.Linketinder.service.MatchService
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "VacancyServlet", urlPatterns = "/vagas/*")
class VacancyServlet extends HttpServlet {

    private VacancyService vacancyService = new VacancyService()
    private MatchService matchService = new MatchService() // Instância necessária para a afinidade
    private JsonSlurper jsonSlurper = new JsonSlurper()

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        // GET /vagas -> Listar todas (sem cálculo de afinidade)
        if (pathInfo == null || pathInfo == "/") {
            def vacancies = vacancyService.getAllVacancies()
            resp.writer.write(JsonOutput.toJson(vacancies))
            return
        }

        String[] parts = pathInfo.split("/")

        try {
            // GET /vagas/afinidade/{candidateId}
            if (parts.length > 2 && "afinidade".equals(parts[1])) {
                Long candidateId = parts[2].toLong()
                
                def vacanciesWithAffinity = matchService.getVacanciesWithAffinity(candidateId)
                
                resp.writer.write(JsonOutput.toJson(vacanciesWithAffinity))
                return
            }
            // ----------------------------------------

            Long id = parts[1].toLong()

            // GET /vagas/{id}/skills -> Listar skills da vaga
            if (parts.length > 2 && "skills".equals(parts[2])) {
                def skills = vacancyService.getSkillsByVacancyId(id)
                resp.writer.write(JsonOutput.toJson(skills))
            } 
            // GET /vagas/{id}/candidatos -> (Opcional) Ver candidatos compatíveis
            else if (parts.length > 2 && "candidatos".equals(parts[2])) {
                def candidates = matchService.getCandidatesWithAffinityForVacancy(id)
                resp.writer.write(JsonOutput.toJson(candidates))
            }
            // GET /vagas/{id} -> Detalhes da vaga simples
            else {
                def vacancy = vacancyService.findVacancyById(id)
                if (vacancy) {
                    resp.writer.write(JsonOutput.toJson(vacancy))
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Vaga não encontrada")
                }
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido na URL")
        } catch (Exception e) {
            e.printStackTrace()
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro no servidor: ${e.message}")
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        // POST /vagas -> Cadastrar nova
        if (pathInfo == null || pathInfo == "/") {
            try {
                def vacancyData = jsonSlurper.parse(req.reader) as Map
                def newVacancy = vacancyService.registerVacancy(vacancyData)
                resp.setStatus(HttpServletResponse.SC_CREATED)
                resp.writer.write(JsonOutput.toJson(newVacancy))
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao criar vaga: ${e.message}")
            }
            return
        }

        // POST /vagas/{id}/skills -> Adicionar Skill
        String[] parts = pathInfo.split("/")
        if (parts.length > 2 && "skills".equals(parts[2])) {
            try {
                Long vacancyId = parts[1].toLong()
                def body = jsonSlurper.parse(req.reader) as Map
                Long skillId = body.skillId?.toLong()

                if (skillId) {
                    vacancyService.addSkillToVacancy(vacancyId, skillId)
                    resp.setStatus(HttpServletResponse.SC_CREATED)
                    resp.writer.write(JsonOutput.toJson([message: "Competência adicionada à vaga"]))
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "skillId obrigatório")
                }
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro: ${e.message}")
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
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.message)
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
            Long vacancyId = parts[1].toLong()

            // DELETE /vagas/{id}/skills
            if (parts.length > 2 && "skills".equals(parts[2])) {
                try {
                    def body = jsonSlurper.parse(req.reader) as Map
                    Long skillId = body.skillId?.toLong()
                    if (skillId) {
                        vacancyService.removeSkillFromVacancy(vacancyId, skillId)
                        resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
                    } else {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "skillId necessário")
                    }
                } catch (Exception e) {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.message)
                }
                return
            }
            
            // DELETE /vagas/{id}
            if (vacancyService.deleteVacancy(vacancyId)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Vaga não encontrada")
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao deletar")
        }
    }
}