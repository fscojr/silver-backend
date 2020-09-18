package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.TipoVeiculo;
import br.gov.prf.silver.service.dto.TipoVeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface TipoVeiculoMapper extends EntityMapper<TipoVeiculoDTO, TipoVeiculo> {
	
}
