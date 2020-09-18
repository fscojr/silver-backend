package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.SituacaoCancelamento;
import br.gov.prf.silver.service.dto.SituacaoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface SituacaoCancelamentoMapper extends EntityMapper<SituacaoDTO, SituacaoCancelamento> {
	
}
