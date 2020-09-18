package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.ContratoPatio;
import br.gov.prf.silver.service.dto.ContratoPatioDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface ContratoPatioMapper extends EntityMapper<ContratoPatioDTO, ContratoPatio> {
}
