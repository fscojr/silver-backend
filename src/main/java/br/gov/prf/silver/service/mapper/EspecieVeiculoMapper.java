package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.EspecieVeiculo;
import br.gov.prf.silver.service.dto.EspecieVeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface EspecieVeiculoMapper extends EntityMapper<EspecieVeiculoDTO, EspecieVeiculo> {
	
}
