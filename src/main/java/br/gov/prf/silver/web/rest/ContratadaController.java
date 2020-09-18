package br.gov.prf.silver.web.rest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.dprf.motor.cliente.dominio.ListaPessoasJuridicas;
import br.gov.dprf.motor.cliente.dominio.Pessoa;
import br.gov.prf.silver.service.ContratadaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
@RestController
@RequestMapping("/contratadas")
@Api(value = "/contratadas", tags = {"Contratada"})
@SwaggerDefinition(tags = {
    @Tag(name = "Contratada", description = "Contratada")
})
public class ContratadaController {

    @Autowired
    private ContratadaService contratadaService;

    @GetMapping(value = "/cnpj/{cnpj}")
    public ResponseEntity<Optional<ListaPessoasJuridicas>> consultaCnpjNaReceita(@PathVariable String cnpj) {
        return ResponseEntity.ok(this.contratadaService.consultarCnpjNaReceita(cnpj));
    }

    @GetMapping(value = "/cpf/{cpf}")
    public ResponseEntity<Optional<Pessoa>> consultaCPFNaReceita(@PathVariable String cpf) {
        return ResponseEntity.ok(this.contratadaService.consultarCPFNaReceita(cpf));
    }

}
