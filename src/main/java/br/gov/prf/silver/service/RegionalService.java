package br.gov.prf.silver.service;

import java.util.List;

import br.gov.prf.silver.service.dto.RegionalDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

public interface RegionalService  {
	
	List<RegionalDTO> recuperarListaRegionais() throws InterruptedException;

}
