
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

        if (pathInfo == null || pathInfo == "/") {
            // GET /candidatos -> Listar todos
            def candidates = candidateService.getAllCandidates()
            resp.writer.write(JsonOutput.toJson(candidates))
        } else {
            // GET /candidatos/{id} -> Buscar por ID
            try {
                Long id = pathInfo.substring(1).toLong()
                def candidate = candidateService.findCandidateById(id)
                if (candidate) {
                    resp.writer.write(JsonOutput.toJson(candidate))
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Candidato não encontrado")
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de candidato inválido")
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        // POST /candidatos -> Cadastrar novo
        try {
            def dadosCandidato = jsonSlurper.parse(req.reader) as Map
            candidateService.registerCandidate(dadosCandidato)
            resp.setStatus(HttpServletResponse.SC_CREATED)
            resp.writer.write(JsonOutput.toJson([message: "Candidato cadastrado com sucesso!"]))
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao processar dados do candidato: ${e.message}")
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        if (pathInfo == null || pathInfo == "/") {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do candidato é necessário para atualização")
            return
        }

        // PUT /candidatos/{id} -> Atualizar
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
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de candidato inválido")
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao processar dados de atualização: ${e.message}")
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo

        if (pathInfo == null || pathInfo == "/") {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID do candidato é necessário para exclusão")
            return
        }
        
        // DELETE /candidatos/{id} -> Excluir
        try {
            Long id = pathInfo.substring(1).toLong()
            if (candidateService.deleteCandidate(id)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Candidato não encontrado")
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de candidato inválido")
        }
    }
}
