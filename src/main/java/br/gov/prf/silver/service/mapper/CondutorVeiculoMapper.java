package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.CondutorVeiculo;
import br.gov.prf.silver.service.dto.CondutorVeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface CondutorVeiculoMapper extends EntityMapper<CondutorVeiculoDTO, CondutorVeiculo> {
	
}
