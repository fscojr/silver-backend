package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.Patio;
import br.gov.prf.silver.service.dto.PatioDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface PatioMapper extends EntityMapper<PatioDTO, Patio> {
	
}
