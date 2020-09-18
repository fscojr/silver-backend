package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.ImagemVeiculo;
import br.gov.prf.silver.service.dto.ImagemVeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface ImagemVeiculoMapper extends EntityMapper<ImagemVeiculoDTO, ImagemVeiculo> {
	
}
