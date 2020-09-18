package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.contratoPatio.TrechoCobertura;
import br.gov.prf.silver.service.dto.TrechoCoberturaDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface TrechoCoberturaMapper extends EntityMapper<TrechoCoberturaDTO, TrechoCobertura> {
	
}
