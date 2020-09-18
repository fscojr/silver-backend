package br.gov.prf.silver.web.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@RestController
@RequestMapping("/monitor-solicitacaos")
@Api(value = "/monitor-solicitacaos", tags = {"Monitor Solicitações"})
@SwaggerDefinition(tags = {
    @Tag(name = "Monitor Solicitações", description = "Monitor Solicitações")
})
public class MonitorSolicitacaoController {

}
