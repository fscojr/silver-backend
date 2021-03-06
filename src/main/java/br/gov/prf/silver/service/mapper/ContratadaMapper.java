package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.contratoPatio.Contratada;
import br.gov.prf.silver.service.dto.ContratadaDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */

@Mapper(componentModel = "spring")
public interface ContratadaMapper extends EntityMapper<ContratadaDTO, Contratada> {
}
