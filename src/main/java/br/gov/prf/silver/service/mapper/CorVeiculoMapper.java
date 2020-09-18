package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.CorVeiculo;
import br.gov.prf.silver.service.dto.CorVeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface CorVeiculoMapper extends EntityMapper<CorVeiculoDTO, CorVeiculo> {
	
}
