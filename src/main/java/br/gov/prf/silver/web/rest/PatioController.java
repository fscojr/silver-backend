package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.service.PatioService;
import br.gov.prf.silver.service.ResponsavelPatioService;
import br.gov.prf.silver.service.dto.PatioDTO;
import br.gov.prf.silver.service.dto.dominio.TipoPatioDTO;
import br.gov.prf.silver.service.filtro.PatioFiltro;
import br.gov.prf.silver.web.rest.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@RestController
@RequestMapping("/patios")
@Api(value = "/patios", tags = {"Pátios"})
@SwaggerDefinition(tags = {
    @Tag(name = "Pátios", description = "Pátios")
})
public class PatioController {

    @Autowired
    private PatioService patioService;
    @Autowired
    private ResponsavelPatioService responsavelPatioService;

    @PostMapping
    @ApiOperation(value = "Salva Pátio", response = ResponseEntity.class)
    public ResponseEntity<Response<PatioDTO>> salva(@Valid @RequestBody PatioDTO dto) {
        return ResponseEntity.ok().body(new Response<>(patioService.salvarPatio(dto)));
    }

    @PostMapping("/consultar")
    @ApiOperation(value = "Consulta Pátio", response = ResponseEntity.class)
    public ResponseEntity<Page<PatioDTO>> consulta(@Valid @RequestBody PatioFiltro filtro, Pageable pageable) {
        return ResponseEntity.ok(patioService.consultarPatio(filtro, pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Consulta Pátio por id", response = ResponseEntity.class)
    public ResponseEntity<PatioDTO> consultaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(patioService.consultarPatioPorId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta Pátio")
    public void deleta(@Valid @PathVariable Long id) {
        patioService.deletarPatio(id);
    }

    @GetMapping("/tipos-patio")
    @ApiOperation(value = "Lista tipos de pátio", response = ResponseEntity.class)
    public ResponseEntity<List<TipoPatioDTO>> ListaTiposPatio() {
        return ResponseEntity.ok(patioService.listarTiposPatio());
    }

    @GetMapping("/unidade/{id}")
    @ApiOperation(value = "Consulta Pátio por Unidade", response = ResponseEntity.class)
    public ResponseEntity<List<PatioDTO>> consultaPatioPorUnidade(@PathVariable Long id) {
        return ResponseEntity.ok(patioService.consultarPatioPorUnidade(id));
    }

    @DeleteMapping("/responsavel/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta Responsável Pátio")
    public void deletaResponsavel(@Valid @PathVariable Long id) {
        responsavelPatioService.deletarResponsavelPatio(id);
    }

}
