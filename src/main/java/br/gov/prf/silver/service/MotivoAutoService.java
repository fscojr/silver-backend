package br.gov.prf.silver.service;

import java.util.List;

import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.service.dto.MotivoRecolhimentoAutoDTO;
import br.gov.prf.silver.service.dto.MotivoRecolhimentoDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface MotivoAutoService {

	List<MotivoRecolhimentoAutoDTO> salvar(List<MotivoRecolhimentoAutoDTO> dto, Recolhimento recolhimento);
	
	List<MotivoRecolhimentoDTO> buscarPorRecolhimentoId(Long recolhimentoId);
	
}
