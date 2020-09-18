package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.TaPatioUnidade;
import br.gov.prf.silver.service.dto.TaPatioUnidadeDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface TaPatioUnidadeMapper extends EntityMapper<TaPatioUnidadeDTO, TaPatioUnidade> {
	
}
