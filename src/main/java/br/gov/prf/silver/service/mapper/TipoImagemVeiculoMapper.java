package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.TipoImagemVeiculo;
import br.gov.prf.silver.service.dto.TipoImagemVeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface TipoImagemVeiculoMapper extends EntityMapper<TipoImagemVeiculoDTO, TipoImagemVeiculo> {
	
}
