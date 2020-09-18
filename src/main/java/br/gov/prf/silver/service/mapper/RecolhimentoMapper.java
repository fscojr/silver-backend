package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.service.dto.RecolhimentoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface RecolhimentoMapper extends EntityMapper<RecolhimentoDTO, Recolhimento> {
	
}
