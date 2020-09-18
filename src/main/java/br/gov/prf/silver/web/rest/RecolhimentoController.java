package br.gov.prf.silver.web.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.prf.silver.exceptions.BusinessException;
import br.gov.prf.silver.service.RecolhimentoService;
import br.gov.prf.silver.service.RelatorioService;
import br.gov.prf.silver.service.dto.DadosRelatorioPesquisaDTO;
import br.gov.prf.silver.service.dto.RecolhimentoDTO;
import br.gov.prf.silver.service.dto.SituacaoDTO;
import br.gov.prf.silver.service.filtro.RecolhimentoFiltro;
import br.gov.prf.silver.web.rest.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import net.sf.jasperreports.engine.JRException;

/**
 * Review by bruno.abreu.prestador on April/2020
 */

@RestController
@RequestMapping("/recolhimentos")
@Api(value = "/recolhimentos", tags = { "Recolhimento" })
@SwaggerDefinition(tags = {
    @Tag(name = "Recolhimento", description = "Recolhimento")
})
public class RecolhimentoController {

    public static final String CAMINHO_RELATORIO_CONSULTA_RECOLHIMENTO = 
    		"/templates/reports/relatorioRecolhimentoPesquisa.jrxml";
    private static final String TEMPLATE_RELATORIO_CONSULTA_RECOLHIMENTO = 
    		"relatorioRecolhimentoPesquisa.jasper";

    private RecolhimentoService recolhimentoService;
    private RelatorioService relatorioService;

    @Autowired
    public RecolhimentoController(
    		RecolhimentoService recolhimentoService,
    	    RelatorioService relatorioService) {
    	this.recolhimentoService = recolhimentoService;
        this.relatorioService = relatorioService;
    }
    
    @PostMapping
    @ApiOperation(value = "Salva/Edita Recolhimento", response = ResponseEntity.class)
    public ResponseEntity<Response<RecolhimentoDTO>> salvaRecolhimento(
    		@Valid @RequestBody RecolhimentoDTO dto) {
    	
        return ResponseEntity.ok().body(new Response<>(recolhimentoService.salva(dto)));
    }

    @PostMapping(path = "/consultar")
    @ApiOperation(value = "Pesquisa recolhimento", response = ResponseEntity.class)
    public ResponseEntity<Page<RecolhimentoDTO>> consultaRecolhimento(
    		@Valid @RequestBody RecolhimentoFiltro filtro, Pageable pageable) {
    	
        return ResponseEntity.ok(recolhimentoService.consulta(filtro, pageable));
    }

    @GetMapping(path = "/id/{id}")
    @ApiOperation(value = "Consultar recolhimento por id", response = ResponseEntity.class)
    public ResponseEntity<RecolhimentoDTO> consultaPorId(@PathVariable Long id) throws IOException {
    	
        return ResponseEntity.ok().body(this.recolhimentoService.consultaPorId(id));
    }

    @GetMapping(path = "/{drv}")
    @ApiOperation(value = "Consultar recolhimento pelo DRV", response = ResponseEntity.class)
    public ResponseEntity<RecolhimentoDTO> consultaRecolhimentoPeloDrv(@PathVariable String drv) {
    	
        return ResponseEntity.ok(recolhimentoService.consultaPeloDrv(drv));
    }
    
    @GetMapping(path = "/situacoes")
    @ApiOperation(value = "Consultar situações recolhimento", response = ResponseEntity.class)
    public ResponseEntity<List<SituacaoDTO>> consultaSituacaoRecolhimento() {
        return ResponseEntity.ok(recolhimentoService.consultaSituacaoRecolhimento());
    }

    @PostMapping(path = "/relatorio-consulta/pdf")
    @ApiOperation(value = "Gera relatório em PDF da pesquisa", response = ResponseEntity.class)
    public @ResponseBody
    byte[] geraArquivoPDF(@Valid @RequestBody RecolhimentoFiltro filtro, HttpServletResponse response)
        throws JRException, IOException, BusinessException {
    	
        List<DadosRelatorioPesquisaDTO> dadosRelatorio = recolhimentoService.recuperaDadosRelatorio(filtro);
        response.setContentType("application/x-pdf");
        response.setHeader(
        		"Content-Disposition", "inline; filename=relatorio-consulta-motivo-recolhimento.pdf");
        return relatorioService.gerarRelatorioPDF(
        		dadosRelatorio, null, CAMINHO_RELATORIO_CONSULTA_RECOLHIMENTO, null);
    }

    @PostMapping(path = "/relatorio-consulta/xls")
    @ApiOperation(value = "Gera relatório em XLS da pesquisa recolhimento")
    public void geraArquivoXls(@RequestBody RecolhimentoFiltro filtro, HttpServletResponse response)
        throws JRException, IOException {
    	
        response.setContentType("application/vnd.ms-excel");
        response.setHeader(
        		"Content-Disposition", "attachment; filename=relatorio-consulta-motivo-recolhimento.xls");
        List<DadosRelatorioPesquisaDTO> dadosRelatorio = recolhimentoService.recuperaDadosRelatorio(filtro);
        this.relatorioService.geraXLS(response, dadosRelatorio, TEMPLATE_RELATORIO_CONSULTA_RECOLHIMENTO);
    }
    
}
