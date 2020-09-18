package br.gov.prf.silver.web.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.dprf.wsclient.servo.dominio.UfBr;
import br.gov.prf.silver.service.TrechoCoberturaService;
import br.gov.prf.silver.service.dto.TrechoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
@RestController
@RequestMapping("/trechos")
@Api(value = "/trechos", tags = {"Trechos"})
@SwaggerDefinition(tags = {
    @Tag(name = "Trechos", description = "Trechos")
})
public class TrechoController {

    private TrechoCoberturaService service;

    @Autowired
    public TrechoController(TrechoCoberturaService service) {
    	this.service = service;
    }
    
    @GetMapping(value = "/{uf}/{br}/{km}")
    @ApiOperation(value = "Consulta Trechos", response = ResponseEntity.class)
    public ResponseEntity<List<TrechoDTO>> consulta(@PathVariable String uf, 
    		@PathVariable String br, @PathVariable Long km) throws IOException {
    	
        return ResponseEntity.ok(this.service.consultar(uf, br, km));
    }

    @GetMapping(value = "/br/{uf}")
    @ApiOperation(value = "Consulta De UF que retorna as BRs daquela UF", response = ResponseEntity.class)
    public ResponseEntity<List<UfBr>> consultaBrPorUf(@PathVariable String uf) {

    	return ResponseEntity.ok(this.service.consultaBrPorUf(uf));
    }

}
