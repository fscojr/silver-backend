package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.CategoriaVeiculo;
import br.gov.prf.silver.service.dto.CategoriaVeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface CategoriaVeiculoMapper extends EntityMapper<CategoriaVeiculoDTO, CategoriaVeiculo> {
	
}
