package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.service.CandidateService
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "CandidatoServlet", urlPatterns = "/candidatos/*")
class CandidatoServlet extends HttpServlet {

    private CandidateService candidateService = new CandidateService()
    private JsonSlurper jsonSlurper = new JsonSlurper()

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        // GET /candidatos -> Listar todos
        if (pathInfo == null || pathInfo == "/") {
            def candidates = candidateService.getAllCandidates()
            resp.writer.write(JsonOutput.toJson(candidates))
            return
        }

        String[] parts = pathInfo.split("/")

        try {
            Long id = parts[1].toLong()

            // Verifica se é GET /candidatos/{id}/skills
            if (parts.length > 2 && "skills".equals(parts[2])) {
                def skills = candidateService.getSkillsByCandidateId(id)
                resp.writer.write(JsonOutput.toJson(skills))
            } 
            // Senão, é GET /candidatos/{id} (Normal)
            else {
                def candidate = candidateService.findCandidateById(id)
                if (candidate) {
                    resp.writer.write(JsonOutput.toJson(candidate))
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Candidato não encontrado")
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

        // POST /candidatos -> Cadastrar novo candidato 
        if (pathInfo == null || pathInfo == "/") {
            try {
                def dadosCandidato = jsonSlurper.parse(req.reader) as Map
                candidateService.registerCandidate(dadosCandidato)
                resp.setStatus(HttpServletResponse.SC_CREATED)
                resp.writer.write(JsonOutput.toJson([message: "Candidato cadastrado com sucesso!"]))
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao criar candidato: ${e.message}")
            }
            return
        }

        //  POST /candidatos/{id}/skills -> Associar competência
        String[] parts = pathInfo.split("/")
        if (parts.length > 2 && "skills".equals(parts[2])) {
            try {
                Long candidateId = parts[1].toLong()
                def body = jsonSlurper.parse(req.reader) as Map
                
                // Espera receber { "skillId": 5 }
                Long skillId = body.skillId?.toLong()

                if (skillId) {
                    candidateService.addSkillToCandidate(candidateId, skillId)
                    resp.setStatus(HttpServletResponse.SC_CREATED)
                    resp.writer.write(JsonOutput.toJson([message: "Competência adicionada ao candidato com sucesso"]))
                } else {
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "O campo 'skillId' é obrigatório")
                }
            } catch (Exception e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao associar competência: ${e.message}")
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

        // PUT /candidatos/{id}
        try {
            Long id = pathInfo.substring(1).toLong()
            def novosDados = jsonSlurper.parse(req.reader) as Map
            def candidatoAtualizado = candidateService.updateCandidate(id, novosDados)

            if (candidatoAtualizado) {
                resp.writer.write(JsonOutput.toJson(candidatoAtualizado))
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Candidato não encontrado")
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido")
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro na atualização: ${e.message}")
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

            // DELETE /candidatos/{id}/skills -> Remover competência
            // Espera corpo JSON: { "skillId": 5 }
            if (parts.length > 2 && "skills".equals(parts[2])) {
                 try {
                     def body = jsonSlurper.parse(req.reader) as Map
                     Long skillId = body.skillId?.toLong()
                     
                     if (skillId) {
                         candidateService.removeSkillFromCandidate(id, skillId)
                         resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
                     } else {
                         resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "skillId necessário")
                     }
                 } catch (Exception e) {
                     resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao remover skill: ${e.message}")
                 }
                 return
            }

            // DELETE /candidatos/{id} -> Excluir Candidato
            if (candidateService.deleteCandidate(id)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Candidato não encontrado")
            }

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID inválido")
        }
    }
}