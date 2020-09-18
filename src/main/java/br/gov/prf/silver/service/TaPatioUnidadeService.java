package br.gov.prf.silver.service;

import java.util.List;

import javax.validation.Valid;

import br.gov.prf.silver.domain.Patio;
import br.gov.prf.silver.service.dto.TaPatioUnidadeDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface TaPatioUnidadeService {

    List<TaPatioUnidadeDTO> salvarPatioUnidade(List<TaPatioUnidadeDTO> patioUnidade, Patio patio);
    
    List<TaPatioUnidadeDTO> consultarPatioUnidadePorIdDoPatio(Long idPatio);
    
    void deletarPatioUnidade(@Valid Long id);

}
