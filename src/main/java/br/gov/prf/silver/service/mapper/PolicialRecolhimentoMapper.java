package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.PolicialRecolhimento;
import br.gov.prf.silver.service.dto.PolicialRecolhimentoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface PolicialRecolhimentoMapper extends EntityMapper<PolicialRecolhimentoDTO, PolicialRecolhimento> {
	
}
