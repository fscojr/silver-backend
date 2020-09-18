package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.RemocaoVeiculo;
import br.gov.prf.silver.service.dto.RemocaoVeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface RemocaoVeiculoMapper extends EntityMapper<RemocaoVeiculoDTO, RemocaoVeiculo> {
	
}
