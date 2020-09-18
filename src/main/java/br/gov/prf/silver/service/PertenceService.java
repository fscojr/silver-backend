package br.gov.prf.silver.service;

import java.util.List;

import br.gov.prf.silver.service.dto.CombinacaoDTO;
import br.gov.prf.silver.service.dto.PertenceDTO;
import br.gov.prf.silver.service.dto.VeiculoDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface PertenceService {

	List<PertenceDTO> salvar(List<PertenceDTO> pertenceList, 
			VeiculoDTO veiculo, CombinacaoDTO combinacao);
	
	List<PertenceDTO> consultaVeiculoId(Long veiculoId);

}
