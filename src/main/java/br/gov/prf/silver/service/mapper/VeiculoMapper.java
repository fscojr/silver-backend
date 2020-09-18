package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.Veiculo;
import br.gov.prf.silver.service.dto.VeiculoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface VeiculoMapper extends EntityMapper<VeiculoDTO, Veiculo> {
	
}
