package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.SolicitacaoCancelamento;
import br.gov.prf.silver.service.dto.SolicitacaoCancelamentoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface SolicitacaoCancelamentoMapper extends EntityMapper<SolicitacaoCancelamentoDTO, SolicitacaoCancelamento> {
	
}
