package br.gov.prf.silver.service;

import java.util.List;

import br.gov.prf.silver.service.dto.UnidadeDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

public interface UnidadeService  {
	
	List<UnidadeDTO> buscarUnidadesPelaRegional(Long idRegional);
	
	UnidadeDTO buscarUnidade(Long id);

}
