package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.Combinacao;
import br.gov.prf.silver.service.dto.CombinacaoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface CombinacaoMapper extends EntityMapper<CombinacaoDTO, Combinacao> {
	
}
