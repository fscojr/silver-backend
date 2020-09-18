package br.gov.prf.silver.web.rest;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.gov.prf.silver.service.SolicitacaoCancelamentoService;
import br.gov.prf.silver.service.dto.SituacaoDTO;
import br.gov.prf.silver.service.dto.SituacaoSolicitacaoDTO;
import br.gov.prf.silver.service.dto.SolicitacaoCancelamentoDTO;
import br.gov.prf.silver.service.filtro.MonitorSolicitacaoFiltro;
import br.gov.prf.silver.web.rest.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@RestController
@RequestMapping("/solicitacao-cancelamentos")
@Api(value="/solicitacao-cancelamentos", tags = {"Solicitações de Cancelamento"})
@SwaggerDefinition(tags = {
    @Tag(name = "Solicitações de Cancelamento",
        description = "Solicitações de Cancelamento")
})
public class SolicitacaoCancelamentoController {

    @Autowired private SolicitacaoCancelamentoService service;

    
    @PostMapping
    @ApiOperation(value = "Salva solicitação de cancelamento do recolhimento selecionado.",
        response = ResponseEntity.class)
    public ResponseEntity<Response<SituacaoSolicitacaoDTO>> salva(
        @Valid @RequestBody SolicitacaoCancelamentoDTO dto) throws MessagingException {
        return ResponseEntity.ok().body(new Response<>(service.salvar(dto)));
    }

    @PostMapping("/consultar")
    @ApiOperation(value = "Consulta Solicitação", response = ResponseEntity.class)
    public ResponseEntity<Page<SituacaoSolicitacaoDTO>> consulta(
        @Valid @RequestBody MonitorSolicitacaoFiltro filtro, Pageable pageable) {
    	
        return ResponseEntity.ok(service.consultar(filtro, pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Consulta Solicitação por id", response = ResponseEntity.class)
    public ResponseEntity<SolicitacaoCancelamentoDTO> consultaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.consultarPorId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta Solicitação")
    public void deleta(@Valid @PathVariable Long id) {
        service.deletar(id);
    }
    
    @GetMapping("/situacao/{id}")
    @ApiOperation(value = "Consulta Situação por id", response = ResponseEntity.class)
    public ResponseEntity<SituacaoSolicitacaoDTO> consultaSituacaoPorId(@PathVariable Long id) {
       return ResponseEntity.ok(service.consultarSituacaoPorId(id));
    }
    
    @PostMapping("/situacao")
    @ApiOperation(value = "Salva situação da solicitação cancelamento",
        response = ResponseEntity.class)
    public ResponseEntity<Response<SituacaoSolicitacaoDTO>> salvaSituacao(
        @Valid @RequestBody SituacaoSolicitacaoDTO dto) throws MessagingException {
        return ResponseEntity.ok().body(new Response<>(service.salvarSituacao(dto)));
    }
    
    @GetMapping(value = "/situacoes")
    @ApiOperation(value = "Retorna lista de Situações")
    public ResponseEntity<List<SituacaoDTO>> buscaPais() {
        return ResponseEntity.ok(this.service.listaSituacoes());
    }
}
