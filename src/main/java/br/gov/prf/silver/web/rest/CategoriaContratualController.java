package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.service.CategoriaContratualService;
import br.gov.prf.silver.service.dto.CategoriaContratualDTO;
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
@RequestMapping("/categoria-contratuais")
@Api(value = "/categoria-contratuais", tags = {"Categoria Contratual"})
@SwaggerDefinition(tags = {
    @Tag(name = "Categoria Contratual", description = "Categoria Contratual")
})
public class CategoriaContratualController {

    @Autowired
    private CategoriaContratualService categoriaContratualService;

    @GetMapping
    public ResponseEntity<List<CategoriaContratualDTO>> carregaListaCategoriaContratual() {
        return ResponseEntity.ok(this.categoriaContratualService.carregarListaCategoriaContratual());
    }

}

