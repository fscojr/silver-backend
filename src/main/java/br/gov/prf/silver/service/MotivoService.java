package br.gov.prf.silver.service;

import java.util.List;

import br.gov.prf.silver.service.dto.MotivoDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface MotivoService {

	List<MotivoDTO> listaMotivos();
	
	MotivoDTO consultarMotivoPeloId(Long id);
	
}
