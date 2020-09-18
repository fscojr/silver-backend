package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.service.TipoPertenceService;
import br.gov.prf.silver.service.dto.TipoPertenceDTO;
import io.swagger.annotations.Api;
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
@RequestMapping("/pertences")
@Api(value = "/pertences", tags = {"Pertences"})
@SwaggerDefinition(tags = {
    @Tag(name = "Pertences", description = "Pertences")
})
public class PertenceController {

    @Autowired
    private TipoPertenceService tipoPertenceService;

    @GetMapping(value = "/tipos-pertence")
    public ResponseEntity<List<TipoPertenceDTO>> recuperaListaTipoPertence() {
        return ResponseEntity.ok(this.tipoPertenceService.recuperarListaTipoPertence());
    }

}
