package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.Pertence;
import br.gov.prf.silver.service.dto.PertenceDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface PertenceMapper extends EntityMapper<PertenceDTO, Pertence> {
	
}
