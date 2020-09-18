package br.gov.prf.silver.web.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.prf.silver.domain.EnderecoPatio;
import br.gov.prf.silver.service.EnderecoService;
import br.gov.prf.silver.service.dto.MunicipioDTO;
import br.gov.prf.silver.service.dto.PaisDTO;
import br.gov.prf.wsclient.servo2.dominio.Uf;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@RestController
@RequestMapping("/enderecos")
@Api(value = "/enderecos", tags = {"Endereço"})
@SwaggerDefinition(tags = {
    @Tag(name = "Endereço", description = "Endereço")
})
public class EnderecoController {

    @Autowired
    EnderecoService enderecoService;

    @GetMapping(value = "/uf")
    @ApiOperation(value = "Recupera lista de todas UFs", response = ResponseEntity.class)
    public ResponseEntity<List<Uf>> buscaTodasUfs() {
        return ResponseEntity.ok(this.enderecoService.buscarTodasUfs());
    }

    @GetMapping(value = "/municipio/{uf}")
    @ApiOperation(value = "Recupera lista de todas UFs", response = ResponseEntity.class)
    public ResponseEntity<List<MunicipioDTO>> buscaMunicipiosPelaUf(@PathVariable String uf) throws InterruptedException {
        return ResponseEntity.ok(this.enderecoService.buscarMunicipiosPelaUfs(uf));
    }

    @GetMapping(value = "/cep/{cep}")
    @ApiOperation(value = "Retorna Endereço para o CEP informado")
    public ResponseEntity<EnderecoPatio> buscaEnderecoPeloCep(@PathVariable String cep) throws IOException, InterruptedException {
        return ResponseEntity.ok(this.enderecoService.buscarEnderecoPeloCep(cep));
    }

    @GetMapping(value = "/pais")
    @ApiOperation(value = "Retorna lista de Países")
    public ResponseEntity<List<PaisDTO>> buscaPais() {
        return ResponseEntity.ok(this.enderecoService.listaPaises());
    }

    @GetMapping(value = "/municipio-nome/{nome}")
    @ApiOperation(value = "Retorna Municipio")
    public ResponseEntity<List<MunicipioDTO>> buscaMunicipioNome(@PathVariable String nome) 
    		throws InterruptedException {
        return ResponseEntity.ok(this.enderecoService.buscarMunicipioNome(nome));
    }
    
    @GetMapping(value = "/municipio-codigo/{codIbge}")
    @ApiOperation(value = "Retorna Municipio")
    public ResponseEntity<MunicipioDTO> buscaMunicipioIbgeAntigo(@PathVariable String codIbge) {
        return ResponseEntity.ok(this.enderecoService.buscarMunicipioCodigoIbge(codIbge));
    }
}
