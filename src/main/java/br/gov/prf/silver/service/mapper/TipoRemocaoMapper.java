package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.TipoRemocao;
import br.gov.prf.silver.service.dto.TipoRemocaoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface TipoRemocaoMapper extends EntityMapper<TipoRemocaoDTO, TipoRemocao> {
	
}
