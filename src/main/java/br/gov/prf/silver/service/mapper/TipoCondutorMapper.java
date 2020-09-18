package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.TipoCondutor;
import br.gov.prf.silver.service.dto.TipoCondutorDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface TipoCondutorMapper extends EntityMapper<TipoCondutorDTO, TipoCondutor> {
	
}
