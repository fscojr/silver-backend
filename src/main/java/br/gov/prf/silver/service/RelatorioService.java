package br.gov.prf.silver.service;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;

public interface RelatorioService {

    byte[] gerarRelatorioPDF(List<?> dados, String nomeRelatorio, String caminhoRelatorio, 
    		String diretorioSubreport) throws JRException, IOException;
    
    void geraXLS(HttpServletResponse response, List<?> beanCollection, String template) 
    		throws JRException, IOException;
    
}