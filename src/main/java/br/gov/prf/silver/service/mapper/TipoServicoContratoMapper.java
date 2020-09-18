package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.contratoPatio.TipoServicoContrato;
import br.gov.prf.silver.service.dto.TipoServicoContratoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface TipoServicoContratoMapper extends EntityMapper<TipoServicoContratoDTO, TipoServicoContrato> {
	
}
