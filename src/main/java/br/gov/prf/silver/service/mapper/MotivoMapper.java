package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.Motivo;
import br.gov.prf.silver.service.dto.MotivoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface MotivoMapper extends EntityMapper<MotivoDTO, Motivo> {
	
}
