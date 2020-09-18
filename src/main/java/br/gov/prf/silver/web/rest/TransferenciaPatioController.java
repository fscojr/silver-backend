package br.gov.prf.silver.web.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.prf.silver.service.TransferenciaPatioService;
import br.gov.prf.silver.service.dto.TransferenciaPatioDTO;
import br.gov.prf.silver.web.rest.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@RestController
@RequestMapping("/transferencia-patios")
@Api(value = "/transferencia-patios", tags = {"Transferir Veículo de Pátio"})
@SwaggerDefinition(tags = {
    @Tag(name = "Transferir Veículo de Pátio", description = "Transferir Veículo de Pátio")
})
public class TransferenciaPatioController {

    private TransferenciaPatioService transferenciaPatioService;

    @Autowired 
    public TransferenciaPatioController(TransferenciaPatioService transferenciaPatioService) {
        
    	this.transferenciaPatioService = transferenciaPatioService;
    }
    
    @ApiOperation(value = "Salva transferência veíclo de pátio", response = ResponseEntity.class)
    public ResponseEntity<Response<TransferenciaPatioDTO>> salva(@Valid @RequestBody TransferenciaPatioDTO dto) {
        
    	return ResponseEntity.ok().body(new Response<>(transferenciaPatioService.salva(dto)));
    }

}
