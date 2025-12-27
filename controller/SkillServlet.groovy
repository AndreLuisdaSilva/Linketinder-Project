package com.example.demo.exerciciosgroovy.Linketinder.controller

import com.example.demo.exerciciosgroovy.Linketinder.service.SkillService
import groovy.json.JsonSlurper
import groovy.json.JsonOutput

import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "SkillServlet", urlPatterns = "/skills/*")
class SkillServlet extends HttpServlet {

    private SkillService skillService = new SkillService()
    private JsonSlurper jsonSlurper = new JsonSlurper()

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        if (pathInfo == null || pathInfo == "/") {
            // GET /skills -> Listar todas as competências
            def skills = skillService.getAllSkills()
            resp.writer.write(JsonOutput.toJson(skills))
        } else {
            // GET /skills/{id} -> Buscar por ID
            try {
                Long id = pathInfo.substring(1).toLong()
                def skill = skillService.findSkillById(id)
                if (skill) {
                    resp.writer.write(JsonOutput.toJson(skill))
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Competência não encontrada")
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de competência inválido")
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        // POST /skills -> Cadastrar nova competência
        try {
            def skillData = jsonSlurper.parse(req.reader) as Map
            def newSkill = skillService.createSkill(skillData)
            resp.setStatus(HttpServletResponse.SC_CREATED)
            resp.writer.write(JsonOutput.toJson(newSkill))
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.message)
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao criar competência: ${e.message}")
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da competência é necessário para atualização")
            return
        }

        // PUT /skills/{id} -> Atualizar competência
        try {
            Long id = pathInfo.substring(1).toLong()
            def skillData = jsonSlurper.parse(req.reader) as Map
            def updatedSkill = skillService.updateSkill(id, skillData)

            if (updatedSkill) {
                resp.writer.write(JsonOutput.toJson(updatedSkill))
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Competência não encontrada")
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de competência inválido")
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao processar dados de atualização: ${e.message}")
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo

        if (pathInfo == null || pathInfo.equals("/")) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da competência é necessário para exclusão")
            return
        }

        // DELETE /skills/{id} -> Excluir competência
        try {
            Long id = pathInfo.substring(1).toLong()
            if (skillService.deleteSkill(id)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Competência não encontrada")
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de competência inválido")
        }
    }
}
