package br.gov.prf.silver.service;

import java.util.List;

import br.gov.prf.silver.service.dto.RemocaoVeiculoDTO;
import br.gov.prf.silver.service.dto.TipoRemocaoDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface RemocaoVeiculoService {
   
	List<TipoRemocaoDTO> recuperarListaTiposRemocao();

    RemocaoVeiculoDTO salvarRemocao(RemocaoVeiculoDTO dto);
    
}
