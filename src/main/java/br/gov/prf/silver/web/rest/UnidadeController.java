package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.service.TaPatioUnidadeService;
import br.gov.prf.silver.service.UnidadeService;
import br.gov.prf.silver.service.dto.UnidadeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@RestController
@RequestMapping("/unidades")
@Api(value = "/unidades", tags = {"Unidades"})
@SwaggerDefinition(tags = {
    @Tag(name = "Unidades", description = "Unidades")
})
public class UnidadeController {

    @Autowired
    private UnidadeService unidadeService;

    @Autowired
    private TaPatioUnidadeService taPatioUnidadeService;

    @GetMapping(value = "regional/{idRegional}")
    @ApiOperation(value = "Recupera lista de Unidades a partir da Regional", response = ResponseEntity.class)
    public ResponseEntity<List<UnidadeDTO>> buscaUnidadesPelaRegional(@PathVariable Long idRegional) {
        return ResponseEntity.ok(this.unidadeService.buscarUnidadesPelaRegional(idRegional));
    }

    @GetMapping(value = "/{id}")
    @ApiOperation(value = "Recupera uma Unidade específica", response = ResponseEntity.class)
    public ResponseEntity<UnidadeDTO> buscaUnidade(@PathVariable Long id) {
        return ResponseEntity.ok(this.unidadeService.buscarUnidade(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta UnidadePatio")
    public void deleta(@Valid @PathVariable Long id) {
        taPatioUnidadeService.deletarPatioUnidade(id);
    }
}
