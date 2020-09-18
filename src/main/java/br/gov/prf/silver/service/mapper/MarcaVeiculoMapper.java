package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.MarcaVeiculo;
import br.gov.prf.silver.service.dto.MarcaVeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface MarcaVeiculoMapper extends EntityMapper<MarcaVeiculoDTO, MarcaVeiculo> {
	
}
