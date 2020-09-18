package br.gov.prf.silver.service;

import java.util.List;

import br.gov.prf.silver.service.dto.CondutorVeiculoDTO;
import br.gov.prf.silver.service.dto.TipoCondutorDTO;
import br.gov.prf.silver.service.dto.TipoDocumentoCondutorDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface CondutorVeiculoService {

	CondutorVeiculoDTO salvarCondutor(CondutorVeiculoDTO dto);
	
	List<TipoCondutorDTO> recuperarListaTiposCondutor();

    TipoCondutorDTO consultarTipoCondutorPorId(Long id);
    
    List<TipoDocumentoCondutorDTO> recuperarListaTiposDocCondutor();
    
}
