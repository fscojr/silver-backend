package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.contratoPatio.ServicoContrato;
import br.gov.prf.silver.service.dto.ServicoContratoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface ServicoContratoMapper extends EntityMapper<ServicoContratoDTO, ServicoContrato> {
	
}
