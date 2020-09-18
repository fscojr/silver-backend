package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.MotivoRecolhimentoHst;
import br.gov.prf.silver.service.dto.MotivoRecolhimentoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface MotivoRecolhimentoHstMapper extends EntityMapper<MotivoRecolhimentoDTO, MotivoRecolhimentoHst> {
	
}
