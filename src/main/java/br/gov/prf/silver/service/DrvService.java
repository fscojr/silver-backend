package br.gov.prf.silver.service;

import java.io.IOException;
import java.util.List;

import br.gov.prf.silver.exceptions.BusinessException;
import br.gov.prf.silver.service.dto.RelatorioDrvDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface DrvService {

    List<RelatorioDrvDTO> gerarDadosRelatorio(Long id) throws IOException, BusinessException;
    
}
