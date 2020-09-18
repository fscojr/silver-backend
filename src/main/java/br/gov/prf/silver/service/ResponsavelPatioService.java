package br.gov.prf.silver.service;

import java.util.List;

import javax.validation.Valid;

import br.gov.prf.silver.domain.Patio;
import br.gov.prf.silver.service.dto.ResponsavelPatioDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface ResponsavelPatioService {

    List<ResponsavelPatioDTO> salvarResponsavelPatio(List<ResponsavelPatioDTO> reponsavelPatio, Patio patio);
    
    List<ResponsavelPatioDTO> consultarResponsavelPatioPeloIdDoPatio(Long idPatio);
    
    void deletarResponsavelPatio(@Valid Long id);

}
