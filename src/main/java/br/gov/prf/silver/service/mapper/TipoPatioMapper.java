package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.TipoPatio;
import br.gov.prf.silver.service.dto.dominio.TipoPatioDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface TipoPatioMapper extends EntityMapper<TipoPatioDTO, TipoPatio> {
	
}
