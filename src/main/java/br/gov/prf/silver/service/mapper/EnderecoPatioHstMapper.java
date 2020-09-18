package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.EnderecoPatioHst;
import br.gov.prf.silver.service.dto.EnderecoPatioDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface EnderecoPatioHstMapper extends EntityMapper<EnderecoPatioDTO, EnderecoPatioHst> {
	
}
