package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.SituacaoSolicitacao;
import br.gov.prf.silver.service.dto.SituacaoSolicitacaoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface SituacaoSolicitacaoMapper extends EntityMapper<SituacaoSolicitacaoDTO, SituacaoSolicitacao> {
	
}
