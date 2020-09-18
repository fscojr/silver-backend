package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.TipoPertence;
import br.gov.prf.silver.service.dto.TipoPertenceDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface TipoPertenceMapper extends EntityMapper<TipoPertenceDTO, TipoPertence> {
	
}
