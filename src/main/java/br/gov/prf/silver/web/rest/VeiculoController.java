package br.gov.prf.silver.web.rest;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.prf.silver.domain.recolhimento.AutoInfracao;
import br.gov.prf.silver.domain.recolhimento.VeiculoAutoInfracao;
import br.gov.prf.silver.exceptions.BusinessException;
import br.gov.prf.silver.service.VeiculoService;
import br.gov.prf.silver.service.dto.CategoriaVeiculoDTO;
import br.gov.prf.silver.service.dto.CorVeiculoDTO;
import br.gov.prf.silver.service.dto.EmplacamentoVeiculoDTO;
import br.gov.prf.silver.service.dto.EspecieVeiculoDTO;
import br.gov.prf.silver.service.dto.MarcaVeiculoDTO;
import br.gov.prf.silver.service.dto.NivelCombustivelDTO;
import br.gov.prf.silver.service.dto.TipoImagemVeiculoDTO;
import br.gov.prf.silver.service.dto.TipoVeiculoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
@RestController
@RequestMapping("/veiculos")
@Api(value = "/veiculos", tags = {"Veículos"})
@SwaggerDefinition(tags = {
    @Tag(name = "Veículos", description = "Veículos")
})
public class VeiculoController {

    private VeiculoService veiculoService;
    
    @Autowired
    public VeiculoController (VeiculoService veiculoService) {
    	this.veiculoService = veiculoService;
    }

    @GetMapping(path = "/auto-infracao/{numeroAuto}")
    @ApiOperation(
    		value = "Consulta veículo pelo auto de infração", 
    		response = ResponseEntity.class)
    public ResponseEntity<AutoInfracao> consultaAutoInfracao(
    		@PathVariable String numeroAuto) throws IOException, BusinessException {
    	
        return ResponseEntity.ok(veiculoService.consultaAutoInfracao(numeroAuto));
    }
    
    @GetMapping(path = "/placa/{placa}")
    @ApiOperation(
    		value = "Consulta veículo pela placa", 
    		response = ResponseEntity.class)
    public ResponseEntity<VeiculoAutoInfracao> consultaVeiculoPelaPlaca(@PathVariable String placa)
        throws IOException, BusinessException {
        return ResponseEntity.ok(this.veiculoService.consultaVeiculoPorPlaca(placa));
    }

    @GetMapping(path = "/chassi/{chassi}")
    @ApiOperation(
    		value = "Consulta veículo pelo chassi", 
    		response = ResponseEntity.class)
    public ResponseEntity<VeiculoAutoInfracao> consultaVeiculoPeloChassi(@PathVariable String chassi)
        throws IOException, BusinessException {
        return ResponseEntity.ok(this.veiculoService.consultaVeiculoPorChassi(chassi));
    }

    @GetMapping(value = "/tipos-veiculo")
    @ApiOperation(value = "Recupera lista tipos de veículo", response = ResponseEntity.class)
    public ResponseEntity<List<TipoVeiculoDTO>> recuperaListaTiposVeiculo() {
        return ResponseEntity.ok(veiculoService.recuperaTipoVeiculo());
    }

    @GetMapping(value = "/niveis-combustivel")
    @ApiOperation(value = "Recupera lista niveis de combustível", response = ResponseEntity.class)
    public ResponseEntity<List<NivelCombustivelDTO>> recuperaListaNiveisCombustivel() {
        return ResponseEntity.ok(veiculoService.recuperaNivelCombustivel());
    }

    @GetMapping(value = "/tipos-imagem")
    @ApiOperation(value = "Recupera lista tipos de imagem", response = ResponseEntity.class)
    public ResponseEntity<List<TipoImagemVeiculoDTO>> recuperaListaTiposImagemVeiculo() {
        return ResponseEntity.ok(veiculoService.recuperaTipoImagemVeiculo());
    }

    @GetMapping(value = "/marca-veiculo")
    @ApiOperation(value = "Recupera lista marca", response = ResponseEntity.class)
    public ResponseEntity<List<MarcaVeiculoDTO>> recuperaMarcaVeiculo() {
        return ResponseEntity.ok(veiculoService.recuperaMarcaVeiculo());
    }

    @GetMapping(value = "/cor-veiculo")
    @ApiOperation(value = "Recupera lista cor", response = ResponseEntity.class)
    public ResponseEntity<List<CorVeiculoDTO>> recuperaCorVeiculo() {
        return ResponseEntity.ok(veiculoService.recuperaCorVeiculo());
    }

    @GetMapping(value = "/especie-veiculo")
    @ApiOperation(value = "Recupera lista espécie", response = ResponseEntity.class)
    public ResponseEntity<List<EspecieVeiculoDTO>> recuperaEspecieVeiculo() {
        return ResponseEntity.ok(veiculoService.recuperaEspecieVeiculo());
    }

    @GetMapping(value = "/categoria-veiculo")
    @ApiOperation(value = "Recupera lista categoria", response = ResponseEntity.class)
    public ResponseEntity<List<CategoriaVeiculoDTO>> recuperaCategoriaVeiculo() {
        return ResponseEntity.ok(veiculoService.recuperaCategoriaVeiculo());
    }

    @GetMapping(value = "/emplacamento-veiculo")
    @ApiOperation(value = "Recupera lista emplacamento", response = ResponseEntity.class)
    public ResponseEntity<List<EmplacamentoVeiculoDTO>> recuperaEmplacamentoVeiculo() {
        return ResponseEntity.ok(veiculoService.recuperaEmplacamentoVeiculo());
    }

}
