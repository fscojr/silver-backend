package br.gov.prf.silver.service;

import java.util.List;

import javax.validation.Valid;

import br.gov.prf.silver.domain.ContratoPatio;
import br.gov.prf.silver.service.dto.DescricaoServicoDTO;
import br.gov.prf.silver.service.dto.ServicoContratoDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface ServicoContratoService {

    List<ServicoContratoDTO> salvarServicoContrato(List<ServicoContratoDTO> servicoContrato, ContratoPatio contratoPatio);

    List<ServicoContratoDTO> consultarServicoPorIdDoContrato(Long id);
    
    List<DescricaoServicoDTO> recuperarListaDescricaoServico();
    
    void deletarServicoContrato(@Valid Long id);

}
