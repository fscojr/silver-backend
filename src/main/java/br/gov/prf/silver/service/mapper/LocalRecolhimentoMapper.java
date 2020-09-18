package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.LocalRecolhimento;
import br.gov.prf.silver.service.dto.LocalRecolhimentoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface LocalRecolhimentoMapper extends EntityMapper<LocalRecolhimentoDTO, LocalRecolhimento> {
	
}
