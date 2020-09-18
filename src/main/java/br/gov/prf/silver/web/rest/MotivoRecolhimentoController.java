package br.gov.prf.silver.web.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.gov.prf.silver.exceptions.BusinessException;
import br.gov.prf.silver.service.MotivoRecolhimentoService;
import br.gov.prf.silver.service.RelatorioService;
import br.gov.prf.silver.service.dto.DadosRelatorioPesquisaDTO;
import br.gov.prf.silver.service.dto.MotivoDTO;
import br.gov.prf.silver.service.dto.MotivoRecolhimentoDTO;
import br.gov.prf.silver.service.filtro.MotivoRecolhimentoFiltro;
import br.gov.prf.silver.web.rest.response.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import net.sf.jasperreports.engine.JRException;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@RestController
@RequestMapping("/motivo-recolhimentos")
@Api(value = "Motivos do recolhimento", tags = {"Motivos do recolhimento"})
@SwaggerDefinition(tags = {
    @Tag(name = "Motivos de Recolhimentos", description = "Motivos do recolhimento")
})
public class MotivoRecolhimentoController {

    public static final String CAMINHO_RELATORIO_CONSULTA_MOTIVO = "/templates/reports/relatorioMotivoRecolhimento.jrxml";
    private static final String TEMPLATE_RELATORIO_CONSULTA_MOTIVO = "relatorioMotivoRecolhimento.jasper";

    @Autowired private MotivoRecolhimentoService motivoRecolhimentoService;
    @Autowired private RelatorioService relatorioService;

    
    @PostMapping
    @ApiOperation(value = "Salvar motivo do recolhimento", response = ResponseEntity.class)
    public ResponseEntity<Response<MotivoRecolhimentoDTO>> salvar(@Valid @RequestBody MotivoRecolhimentoDTO dto) {
        return ResponseEntity.ok().body(new Response<>(motivoRecolhimentoService.salvar(dto)));
    }

    @PostMapping("/consultar")
    @ApiOperation(value = "Pesquisar motivo do recolhimento", response = ResponseEntity.class)
    public ResponseEntity<Page<MotivoRecolhimentoDTO>> consultar(@Valid @RequestBody MotivoRecolhimentoFiltro filtro, Pageable pageable) {
        return ResponseEntity.ok(motivoRecolhimentoService.consultar(filtro, pageable));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Consultar motivo do recolhimento pelo id", response = ResponseEntity.class)
    public ResponseEntity<MotivoRecolhimentoDTO> consultaPeloId(@PathVariable Long id) {
        return ResponseEntity.ok(motivoRecolhimentoService.consultarPeloId(id));
    }

    @GetMapping("/lista-amparos/{id}")
    @ApiOperation(value = "Lista amparos a partir do motivo do recolhimento", response = ResponseEntity.class)
    public ResponseEntity<List<MotivoRecolhimentoDTO>> listaAmparoIdMotivo(@PathVariable Long id) {
        return ResponseEntity.ok(motivoRecolhimentoService.listarAmparoIdMotivo(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deleta motivo do recolhimento", response = ResponseEntity.class)
    public void deleta(@PathVariable Long id) {
        motivoRecolhimentoService.deletar(id);
    }

    @GetMapping("/motivos")
    @ApiOperation(value = "Lista todos motivo do recolhimento", response = ResponseEntity.class)
    public ResponseEntity<List<MotivoDTO>> ListaMotivos() {
        return ResponseEntity.ok(motivoRecolhimentoService.listaMotivos());
    }

    @GetMapping("/motivo/{id}")
    @ApiOperation(value = "Consultar motivo do recolhimento pelo id", response = ResponseEntity.class)
    public ResponseEntity<List<MotivoRecolhimentoDTO>> consultaMotivoPeloId(@PathVariable Long id) {
        return ResponseEntity.ok(motivoRecolhimentoService.consultarMotivoId(id));
    }

    @PostMapping(path = "/relatorio-consulta/pdf")
    @ApiOperation(value = "Obter o relatório da pesquisa PDF", response = ResponseEntity.class)
    public @ResponseBody
    byte[] geraArquivoPDF(@Valid @RequestBody MotivoRecolhimentoFiltro filtro, HttpServletResponse response)
        throws JRException, IOException, BusinessException {
        List<DadosRelatorioPesquisaDTO> dadosRelatorio = motivoRecolhimentoService.recuperaDadosRelatorio(filtro);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "inline; filename=relatorio-consulta-motivo-recolhimento.pdf");
        return relatorioService.gerarRelatorioPDF(dadosRelatorio, null, CAMINHO_RELATORIO_CONSULTA_MOTIVO, null);
    }

    @PostMapping(path = "/relatorio-consulta/xls")
    @ApiOperation(value = "Obter o relatório da pesquisa PDF XLS")
    public void geraArquivoXls(@RequestBody MotivoRecolhimentoFiltro filtro, HttpServletResponse response)
        throws JRException, IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=relatorio-consulta-motivo-recolhimento.xls");
        List<DadosRelatorioPesquisaDTO> dadosRelatorio = motivoRecolhimentoService.recuperaDadosRelatorio(filtro);
        this.relatorioService.geraXLS(response, dadosRelatorio, TEMPLATE_RELATORIO_CONSULTA_MOTIVO);
    }
    
}
