package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.exceptions.BusinessException;
import br.gov.prf.silver.service.DrvService;
import br.gov.prf.silver.service.RelatorioService;
import br.gov.prf.silver.service.dto.RelatorioDrvDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
@RestController
@RequestMapping("/drvs")
@Api(value = "/drvs", tags = {"Recolhimento"})
@SwaggerDefinition(tags = {
    @Tag(name = "Recolhimento", description = "Recolhimento")
})
public class DrvController {

    public static final String CAMINHO_RELATORIO_DRV = "/templates/reports/relatorioDrv.jrxml";

    private DrvService drvService;
    private RelatorioService relatorioService;

    @Autowired 
    public DrvController(
    		DrvService drvService,
    		RelatorioService relatorioService) {
    	
    	this.drvService = drvService;
        this.relatorioService = relatorioService;
    }
    
    @GetMapping(path = "/exportar/{id}")
    @ApiOperation(value = "Gerar DRV", response = ResponseEntity.class)
    public @ResponseBody
    byte[] gerarRelatorioProjetoSalaVerdePDF(@PathVariable Long id, HttpServletResponse response)
        throws JRException, IOException, BusinessException {
        List<RelatorioDrvDTO> dadosRelatorio = drvService.gerarDadosRelatorio(id);
        response.setContentType("application/x-pdf");
        response.setHeader("Content-Disposition", "inline; filename=DRV.pdf");
        return this.relatorioService.gerarRelatorioPDF(dadosRelatorio, null, CAMINHO_RELATORIO_DRV, null);
    }

}
