package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.PatioHst;
import br.gov.prf.silver.service.dto.PatioDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface PatioHstMapper extends EntityMapper<PatioDTO, PatioHst> {
	
}
