package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.service.RemocaoVeiculoService;
import br.gov.prf.silver.service.dto.TipoRemocaoDTO;
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
@RequestMapping("/remocaos")
@Api(value = "/remocaos", tags = {"Remoção"})
@SwaggerDefinition(tags = {
    @Tag(name = "Remoção", description = "Remoção")
})
public class RemocaoVeiculoController {

    @Autowired
    private RemocaoVeiculoService remocaoService;

    @GetMapping(value = "/tipos-remocao")
    public ResponseEntity<List<TipoRemocaoDTO>> recuperaListaTiposRemocao() {
        return ResponseEntity.ok(remocaoService.recuperarListaTiposRemocao());
    }

}
