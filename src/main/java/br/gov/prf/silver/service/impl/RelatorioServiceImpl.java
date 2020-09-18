package br.gov.prf.silver.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.prf.silver.service.RelatorioService;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;

@Service
@Transactional
public class RelatorioServiceImpl implements RelatorioService {

    private static final String BRASAO = "/templates/reports/prf-brasao.png";

    public byte[] gerarRelatorioPDF(List<?> dados, String nomeRelatorio, String caminhoRelatorio, String diretorioSubreport)
        throws JRException, IOException {

        if((dados == null || dados.isEmpty())){
            //log.warn("A coleção de dados do relatório está vazia");
        }
        JasperReport resourceAsStream = this.gerarRelatorioJasperPDF(caminhoRelatorio);
        JRDataSource jrDataSource = new JRBeanCollectionDataSource(dados);
        Map params = this.setParametrosPDF(jrDataSource, nomeRelatorio, diretorioSubreport);
        JasperPrint jasperPrint = JasperFillManager.fillReport(resourceAsStream, params, jrDataSource);
        Locale ptBr = new Locale("pt", "BR");
        jasperPrint.setLocaleCode(ptBr.getCountry());
        return JasperExportManager.exportReportToPdf(jasperPrint);
    }

    private JasperReport gerarRelatorioJasperPDF(String caminhoRelatorio) throws JRException {
        InputStream jasperStream = this.getClass().getResourceAsStream(caminhoRelatorio);
        JasperDesign design = JRXmlLoader.load(jasperStream);
        return JasperCompileManager.compileReport(design);
    }
    
    private Map setParametrosPDF(JRDataSource jrDataSource, String nomeRelatorio, String diretorioSubreport)
        throws IOException {
        BufferedImage brasao = ImageIO.read(getClass().getResource(BRASAO));
        Map<String, Object> params = new HashMap<>();
        params.put("datasource", jrDataSource);
        params.put("identificacaoOrgao", "");
        params.put("nomeRelatorio", nomeRelatorio);
        params.put("brasao", brasao);
        if (diretorioSubreport != null){
            params.put("SUBREPORT_DIR", diretorioSubreport);
        }
        return params;
    }
    
    @Override
	public void geraXLS(HttpServletResponse response, List<?> dadosRelatorio, String template) 
			throws JRException, IOException {
    	Map<String, Object> parameters = new HashMap<>();
		parameters.put("mostrarBrasao", false);
		parameters.put("imagesPath", "templates/reports/");
		
        InputStream jasperStream = this.getClass().getResourceAsStream("/templates/reports/" + template);
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
        parameters.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
        JasperPrint jasperPrint = JasperFillManager
        		.fillReport(jasperReport, parameters, new JRBeanCollectionDataSource(dadosRelatorio));

        OutputStream outStream = response.getOutputStream();

        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outStream));
        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setOnePagePerSheet(true);
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(false);
        configuration.setIgnoreGraphics(false);
        exporter.setConfiguration(configuration);
        exporter.exportReport();
        outStream.flush();
        outStream.close();
    }
    
}