package com.example.demo.exerciciosgroovy.Linketinder.controller
import javax.servlet.annotation.WebServlet
import com.example.demo.exerciciosgroovy.Linketinder.service.VacancyService
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
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

        if (pathInfo == null || pathInfo == "/") {
            // GET /vagas -> Listar todas
            def vacancies = vacancyService.getAllVacancies()
            resp.writer.write(JsonOutput.toJson(vacancies))
        } else {
            // GET /vagas/{id} -> Buscar por ID
            try {
                Long id = pathInfo.substring(1).toLong()
                def vacancy = vacancyService.findVacancyById(id)
                if (vacancy) {
                    resp.writer.write(JsonOutput.toJson(vacancy))
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Vaga não encontrada")
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de vaga inválido")
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        // POST /vagas -> Cadastrar nova
        try {
            def dadosVaga = jsonSlurper.parse(req.reader) as Map
            vacancyService.registerVacancy(dadosVaga)
            resp.setStatus(HttpServletResponse.SC_CREATED)
            resp.writer.write(JsonOutput.toJson([message: "Vaga cadastrada com sucesso!"]))
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao processar dados da vaga: ${e.message}")
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        if (pathInfo == null || pathInfo == "/") {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da vaga é necessário para atualização")
            return
        }

        // PUT /vagas/{id} -> Atualizar
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
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de vaga inválido")
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao processar dados de atualização: ${e.message}")
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo

        if (pathInfo == null || pathInfo == "/") {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da vaga é necessário para exclusão")
            return
        }
        
        // DELETE /vagas/{id} -> Excluir
        try {
            Long id = pathInfo.substring(1).toLong()
            if (vacancyService.deleteVacancy(id)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Vaga não encontrada")
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de vaga inválido")
        }
    }
}
