package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.EstadoVeiculo;
import br.gov.prf.silver.service.dto.EstadoVeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface EstadoVeiculoMapper extends EntityMapper<EstadoVeiculoDTO, EstadoVeiculo> {
	
}
