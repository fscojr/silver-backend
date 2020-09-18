package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.contratoPatio.DescricaoServico;
import br.gov.prf.silver.service.dto.DescricaoServicoDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface DescricaoServicoMapper extends EntityMapper<DescricaoServicoDTO, DescricaoServico> {
	
}
