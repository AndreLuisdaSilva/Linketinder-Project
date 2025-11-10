// package com.example.demo.exerciciosgroovy.Linketinder.controller

// import spock.lang.Specification
// import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
// import com.example.demo.exerciciosgroovy.Linketinder.model.Empresa
// import com.example.demo.exerciciosgroovy.Linketinder.view.Menu

// class SistemaControllerSpec extends Specification {

//     def "cadastrar novo candidato"() {
//         given:
//         def menu = Mock(Menu)
//         menu.lerDadosNovoCandidato() >> [nome: "Teste", email: "teste@email.com", cpf: "000.000.000-00", idade: 30, estado: "SP", cep: "00000-000", descricaoPessoal: "Dev", competencias: ["Java"]]
//         def sistema = new SistemaController(menu)

//         when:
//         sistema.cadastrarNovoCandidato()

//         then:
//         sistema.candidatos.find { it.nome == "Teste" } != null
//     }

//     def "cadastrar nova empresa"() {
//         given:
//         def menu = Mock(Menu)
//         menu.lerDadosNovaEmpresa() >> [nome: "EmpresaTeste", email: "empresa@email.com", cnpj: "00.000.000/0001-00", pais: "Brasil", estado: "SP", cep: "00000-000", descricao: "Empresa de teste", competencias: ["Java"]]
//         def sistema = new SistemaController(menu)

//         when:
//         sistema.cadastrarNovaEmpresa()

//         then:
//         sistema.empresas.find { it.nome == "EmpresaTeste" } != null
//     }
// }


// package com.example.demo.exerciciosgroovy.Linketinder.controller

// import spock.lang.Specification
// import com.example.demo.exerciciosgroovy.Linketinder.model.Candidato
// import com.example.demo.exerciciosgroovy.Linketinder.view.Menu
// import com.example.demo.exerciciosgroovy.Linketinder.service.CandidateService
// import com.example.demo.exerciciosgroovy.Linketinder.service.CompanyService

// class SistemaControllerSpec extends Specification {

//     Menu menu
//     CandidateService candidateService
//     CompanyService companyService

//     SistemaController sistema

//     def setup() {
//         menu = Mock(Menu)
//         candidateService = Mock(CandidateService)
//         companyService = Mock(CompanyService)
        
//         sistema = new SistemaController(menu, candidateService, companyService)
//     }


//     def "deve atualizar um candidato existente com sucesso"() {
//         given: "Um ID de candidato e novos dados"
//         Long idExistente = 1L
//         Map dadosNovos = [name: "Novo Nome", email: "novo@email.com"]
        
//         menu.lerIdCandidato() >> idExistente
        
//         menu.lerDadosAtualizacaoCandidato() >> dadosNovos
        
//         Candidato candidatoAtualizado = new Candidato(id: idExistente, name: dadosNovos.name)
//         candidateService.updateCandidate(idExistente, dadosNovos) >> candidatoAtualizado

//         when: "O método de atualização é chamado"
//         sistema.atualizarCandidato() 

//         then: "O controller deve chamar o serviço para atualizar"
//         1 * candidateService.updateCandidate(idExistente, dadosNovos)

//         and: "O controller deve exibir uma mensagem de sucesso"
//         1 * menu.exibirMensagem("Candidato atualizado com sucesso!")
        
//         and: "Nenhuma mensagem de erro deve ser exibida"
//         0 * menu.exibirMensagem(_ as String, { it.contains("não encontrado") })
//     }

//     def "deve exibir mensagem de erro ao tentar atualizar candidato inexistente"() {
//         given: "Um ID de candidato que não existe e alguns dados"
//         Long idInexistente = 99L
//         Map dadosNovos = [name: "Nome Fantasma"]

//         menu.lerIdCandidato() >> idInexistente

//         menu.lerDadosAtualizacaoCandidato() >> dadosNovos

//         candidateService.updateCandidate(idInexistente, dadosNovos) >> null

//         when: "O método de atualização é chamado"
//         sistema.atualizarCandidato()

//         then: "O controller deve chamar o serviço"
//         1 * candidateService.updateCandidate(idInexistente, dadosNovos)

//         and: "O controller deve exibir uma mensagem de erro"
//         1 * menu.exibirMensagem("Candidato com ID ${idInexistente} não encontrado.")

//         and: "Nenhuma mensagem de sucesso deve ser exibida"
//         0 * menu.exibirMensagem("Candidato atualizado com sucesso!")
//     }

//     def "deve cadastrar um novo candidato corretamente"() {
//         given: "dados de um novo candidato vindos do menu"
//         def dadosCandidato = [name: "Teste", email: "teste@email.com"]
//         menu.lerDadosNovoCandidato() >> dadosCandidato

//         when: "o método de cadastro de candidato é chamado"
//         sistema.cadastrarNovoCandidato() 

//         then: "o candidato deve ser salvo através do serviço"

//         1 * candidateService.registerCandidate(dadosCandidato)
        
//         and: "O menu deve exibir a mensagem de sucesso"
//         1 * menu.exibirMensagem("Candidato cadastrado com sucesso!")
//     }
// }