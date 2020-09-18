package br.gov.prf.silver.service;

import java.util.List;

import br.gov.prf.silver.service.dto.CombinacaoDTO;
import br.gov.prf.silver.service.dto.VeiculoDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface CombinacaoVeiculoService {
	
	List<CombinacaoDTO> salvarCombinacao(List<CombinacaoDTO> combinacaoList, VeiculoDTO veiculo);
    List<CombinacaoDTO> consultarPorVeiculoId(Long id);
	
}
