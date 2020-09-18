package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.MotivoRecolhimentoAuto;
import br.gov.prf.silver.service.dto.MotivoRecolhimentoAutoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface MotivoAutoMapper extends EntityMapper<MotivoRecolhimentoAutoDTO, MotivoRecolhimentoAuto> {
	
}
