package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.service.ServicoContratoService;
import br.gov.prf.silver.service.dto.DescricaoServicoDTO;
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
@RequestMapping("/descricao-servicos")
@Api(value = "/descricao-servicos", tags = {"Descrições Serviço"})
@SwaggerDefinition(tags = {
    @Tag(name = "Descrições Serviço", description = "Descrições Serviço")
})
public class DescricaoServicoContratoController {

    @Autowired
    private ServicoContratoService descricaoServicoContratoService;

    @GetMapping
    public ResponseEntity<List<DescricaoServicoDTO>> recuperaListaDescricaoServico() {
        return ResponseEntity.ok(this.descricaoServicoContratoService.recuperarListaDescricaoServico());
    }

}
