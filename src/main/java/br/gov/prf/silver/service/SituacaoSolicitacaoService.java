package br.gov.prf.silver.service;

import br.gov.prf.silver.service.dto.SituacaoSolicitacaoDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface SituacaoSolicitacaoService {
	
	SituacaoSolicitacaoDTO salvar(SituacaoSolicitacaoDTO dto);
	SituacaoSolicitacaoDTO consultarPorId(Long id);
	
}
