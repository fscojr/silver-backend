package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.contratoPatio.CategoriaContratual;
import br.gov.prf.silver.service.dto.CategoriaContratualDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface CategoriaContratualMapper extends EntityMapper<CategoriaContratualDTO, CategoriaContratual> {
	
}
