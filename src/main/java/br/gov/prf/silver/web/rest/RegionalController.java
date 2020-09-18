package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.service.RegionalService;
import br.gov.prf.silver.service.dto.RegionalDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@RestController
@RequestMapping("/regionais")
@Api(value = "/regiona", tags = {"Regionais"})
@SwaggerDefinition(tags = {
    @Tag(name = "Regionais", description = "Regionais")
})
public class RegionalController {

    @Autowired
    private RegionalService regionalService;

    @GetMapping
    @ApiOperation(value = "Recupera todas Regionais", response = ResponseEntity.class)
    public ResponseEntity<List<RegionalDTO>> recuperaListaRegionais() throws InterruptedException {
        return ResponseEntity.ok(this.regionalService.recuperarListaRegionais());
    }

}
