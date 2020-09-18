package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.ResponsavelPatio;
import br.gov.prf.silver.service.dto.ResponsavelPatioDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface ResponsavelPatioMapper extends EntityMapper<ResponsavelPatioDTO, ResponsavelPatio> {
	
}
