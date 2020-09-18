package br.gov.prf.silver.service.mapper;

import org.mapstruct.Mapper;

import br.gov.prf.silver.domain.recolhimento.TipoDocumentoCondutor;
import br.gov.prf.silver.service.dto.TipoDocumentoCondutorDTO;

/**
 * Create by bruno.abreu.abreu on November, 2019
 */
@Mapper(componentModel = "spring")
public interface TipoDocumentoCondutorMapper extends EntityMapper<TipoDocumentoCondutorDTO, TipoDocumentoCondutor> {
	
}
