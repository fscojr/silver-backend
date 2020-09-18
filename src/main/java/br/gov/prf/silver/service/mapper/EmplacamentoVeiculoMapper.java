package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.EmplacamentoVeiculo;
import br.gov.prf.silver.service.dto.EmplacamentoVeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface EmplacamentoVeiculoMapper extends EntityMapper<EmplacamentoVeiculoDTO, EmplacamentoVeiculo> {
	
}
