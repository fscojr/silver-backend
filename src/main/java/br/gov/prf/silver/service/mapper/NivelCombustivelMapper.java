package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.NivelCombustivel;
import br.gov.prf.silver.service.dto.NivelCombustivelDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface NivelCombustivelMapper extends EntityMapper<NivelCombustivelDTO, NivelCombustivel> {
	
}
