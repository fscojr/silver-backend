package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.exceptions.BusinessException;
import br.gov.prf.silver.service.ContratoPatioService;
import br.gov.prf.silver.service.RelatorioService;
import br.gov.prf.silver.service.ServicoContratoService;
import br.gov.prf.silver.service.TrechoCoberturaService;
import br.gov.prf.silver.service.dto.ContratoPatioDTO;
import br.gov.prf.silver.service.dto.DadosRelatorioPesquisaDTO;
import br.gov.prf.silver.service.dto.TipoServicoContratoDTO;
import br.gov.prf.silver.service.filtro.ContratoPatioFiltro;
import br.gov.prf.silver.web.rest.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@RestController
@RequestMapping("/contrato-patios")
@Api(value = "/contrato-patios", tags = {"Contrato do Pátios"})
@SwaggerDefinition(tags = {
    @Tag(name = "Contrato do Pátios", description = "Contrato do Pátios")
})
public class ContratoPatioController {

    public static final String CAMINHO_RELATORIO_CONSULTA_CONTRATO_PATIO = "/templates/reports/relatorioContratoPatio.jrxml";
    private static final String TEMPLATE_RELATORIO_CONSULTA_CONTRATO_PATIO = "relatorioContratoPatio.jasper";

    @Autowired
    private ContratoPatioService contratoPatioService;
    @Autowired
    private TrechoCoberturaService trechoCoberturaService;
    @Autowired
    private ServicoContratoService servicoContratoService;
    @Autowired
    private RelatorioService relatorioService;

    @PostMapping
    @ApiOperation(value = "Salvar Contrato Pátio", response = ResponseEntity.class)
    public ResponseEntity<Response<ContratoPatioDTO>> salva(@Valid @RequestBody ContratoPatioDTO dto) {
        return ResponseEntity.ok().body(new Response<>(contratoPatioService.salvarContratoPatio(dto)));
    }

    @PostMapping("/consultar")
    @ApiOperation(value = "Pesquisar Contrato Pátio", response = ResponseEntity.class)
    public ResponseEntity<Page<ContratoPatioDTO>> consulta(@Valid @RequestBody ContratoPatioFiltro filtro, Pageable pageable) {
        return ResponseEntity.ok(contratoPatioService.consultarContratoPatio(filtro, pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Consultar Contrato Pátio pela id", response = ResponseEntity.class)
    public ResponseEntity<ContratoPatioDTO> consultaPeloId(@PathVariable Long id) {
        return ResponseEntity.ok(contratoPatioService.consultarContratoPatioPeloId(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta Contrato Pátio")
    public void deleta(@Valid @PathVariable Long id) {
        contratoPatioService.deletarContratoPatio(id);
    }

    @GetMapping("/tipo-servicos")
    @ApiOperation(value = "Pesquisar Tipos Serviços", response = ResponseEntity.class)
    public ResponseEntity<List<TipoServicoContratoDTO>> listaTipoServicos() {
        return ResponseEntity.ok(contratoPatioService.listarTipoServicoContrato());
    }

    @DeleteMapping("/trecho/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta Trecho")
    public void deletaTrecho(@Valid @PathVariable Long id) {
        trechoCoberturaService.deletarTrechoCobertura(id);
    }

    @DeleteMapping("/servico/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta Serviço")
    public void deletaServico(@Valid @PathVariable Long id) {
        servicoContratoService.deletarServicoContrato(id);
    }

    @PostMapping(path = "/relatorio-consulta/pdf")
    @ApiOperation(value = "Obter o relatório PDF da pesquisa", response = ResponseEntity.class)
    public @ResponseBody
    byte[] geraArquivoPDF(@Valid @RequestBody ContratoPatioFiltro filtro, HttpServletResponse response)
        throws JRException, IOException, BusinessException {
        List<DadosRelatorioPesquisaDTO> dadosRelatorio = contratoPatioService.recuperaDadosRelatorio(filtro);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "inline; filename=relatorio-consulta-contrato-patio.pdf");
        return relatorioService.gerarRelatorioPDF(dadosRelatorio, null, CAMINHO_RELATORIO_CONSULTA_CONTRATO_PATIO, null);
    }

    @PostMapping(path = "/relatorio-consulta/xls")
    @ApiOperation(value = "Obter o relatório XLS da pesquisa")
    public void geraArquivoXls(@RequestBody ContratoPatioFiltro filtro, HttpServletResponse response)
        throws JRException, IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio-consulta-contrato-patio.xls");
        List<DadosRelatorioPesquisaDTO> dadosRelatorio = contratoPatioService.recuperaDadosRelatorio(filtro);
        this.relatorioService.geraXLS(response, dadosRelatorio, TEMPLATE_RELATORIO_CONSULTA_CONTRATO_PATIO);
    }

}
