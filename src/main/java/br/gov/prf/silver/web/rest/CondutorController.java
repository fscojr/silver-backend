package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.service.CondutorVeiculoService;
import br.gov.prf.silver.service.dto.TipoCondutorDTO;
import br.gov.prf.silver.service.dto.TipoDocumentoCondutorDTO;
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
@RequestMapping("/condutores")
@Api(value = "/condutores", tags = {"condutores"})
@SwaggerDefinition(tags = {
    @Tag(name = "Condutores", description = "Condutores")
})
public class CondutorController {

    @Autowired
    private CondutorVeiculoService condutorService;

    @GetMapping("/tipos-condutor")
    @ApiOperation(value = "Recupera lista dos tipos de condutor", response = ResponseEntity.class)
    public ResponseEntity<List<TipoCondutorDTO>> recuperaListaTiposCondutor() {
        return ResponseEntity.ok(this.condutorService.recuperarListaTiposCondutor());
    }

    @GetMapping("/tipo-documentos")
    @ApiOperation(value = "Recupera lista dos tipos de Documento Condutor", response = ResponseEntity.class)
    public ResponseEntity<List<TipoDocumentoCondutorDTO>> recuperaListaTiposDocCondutor() {
        return ResponseEntity.ok(this.condutorService.recuperarListaTiposDocCondutor());
    }

}
