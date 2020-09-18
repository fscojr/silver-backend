package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.service.PolicialRecolhimentoService;
import br.gov.prf.silver.service.dto.PolicialRecolhimentoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@RestController
@RequestMapping("/policial-recolhimentos")
@Api(value = "/policial-recolhimentos", tags = {"Policial Recolhimento"})
@SwaggerDefinition(tags = {
    @Tag(name = "Policial Recolhimento", description = "Policial Recolhimento")
})
public class PolicialRecolhimentoController {

    @Autowired
    private PolicialRecolhimentoService policialService;


    @GetMapping(value = "/{cpfMatricula}")
    public ResponseEntity<List<PolicialRecolhimentoDTO>> consultaPolicialPelaMatriculaCpf(@PathVariable String cpfMatricula) {
        return ResponseEntity.ok(this.policialService.consultarPolicialPelaMatriculaCpf(cpfMatricula));
    }

}
