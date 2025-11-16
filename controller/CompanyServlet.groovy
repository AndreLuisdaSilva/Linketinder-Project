package com.example.demo.exerciciosgroovy.Linketinder.controller
import javax.servlet.annotation.WebServlet
import com.example.demo.exerciciosgroovy.Linketinder.service.CompanyService
import groovy.json.JsonSlurper
import groovy.json.JsonOutput
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "CompanyServlet", urlPatterns = "/empresas/*")
class CompanyServlet extends HttpServlet {

    private CompanyService companyService = new CompanyService()
    private JsonSlurper jsonSlurper = new JsonSlurper()

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        if (pathInfo == null || pathInfo == "/") {
            // GET /empresas -> Listar todas
            def companies = companyService.getAllCompanies()
            resp.writer.write(JsonOutput.toJson(companies))
        } else {
            // GET /empresas/{id} -> Buscar por ID
            try {
                Long id = pathInfo.substring(1).toLong()
                def company = companyService.findCompanyById(id)
                if (company) {
                    resp.writer.write(JsonOutput.toJson(company))
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Empresa não encontrada")
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de empresa inválido")
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        // POST /empresas -> Cadastrar nova
        try {
            def dadosEmpresa = jsonSlurper.parse(req.reader) as Map
            companyService.registerCompany(dadosEmpresa)
            resp.setStatus(HttpServletResponse.SC_CREATED)
            resp.writer.write(JsonOutput.toJson([message: "Empresa cadastrada com sucesso!"]))
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao processar dados da empresa: ${e.message}")
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo
        resp.contentType = "application/json"

        if (pathInfo == null || pathInfo == "/") {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da empresa é necessário para atualização")
            return
        }

        // PUT /empresas/{id} -> Atualizar
        try {
            Long id = pathInfo.substring(1).toLong()
            def novosDados = jsonSlurper.parse(req.reader) as Map
            def empresaAtualizada = companyService.updateCompany(id, novosDados)

            if (empresaAtualizada) {
                resp.writer.write(JsonOutput.toJson(empresaAtualizada))
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Empresa não encontrada")
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de empresa inválido")
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Erro ao processar dados de atualização: ${e.message}")
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.pathInfo

        if (pathInfo == null || pathInfo == "/") {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID da empresa é necessário para exclusão")
            return
        }
        
        // DELETE /empresas/{id} -> Excluir
        try {
            Long id = pathInfo.substring(1).toLong()
            if (companyService.deleteCompanyById(id)) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT)
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Empresa não encontrada")
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de empresa inválido")
        }
    }
}
