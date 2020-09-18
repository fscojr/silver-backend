package br.gov.prf.silver.service;

import java.util.List;

import br.gov.prf.silver.service.dto.TipoPertenceDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface TipoPertenceService {
	
    List<TipoPertenceDTO> recuperarListaTipoPertence();
    
}
