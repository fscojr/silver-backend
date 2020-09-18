package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.SituacaoRecolhimento;
import br.gov.prf.silver.service.dto.SituacaoDTO;

/**
 * Create by bruno.abreu.abreu on April, 2020
 */

@Mapper(componentModel = "spring")
public interface SituacaoRecolhimentoMapper extends EntityMapper<SituacaoDTO, SituacaoRecolhimento> {
	
}
