package br.gov.prf.silver.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.prf.silver.service.dto.ContratoPatioDTO;
import br.gov.prf.silver.service.dto.DadosRelatorioPesquisaDTO;
import br.gov.prf.silver.service.dto.TipoServicoContratoDTO;
import br.gov.prf.silver.service.filtro.ContratoPatioFiltro;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface ContratoPatioService {
	
	ContratoPatioDTO salvarContratoPatio(@Valid ContratoPatioDTO dto);
	
	Page<ContratoPatioDTO> consultarContratoPatio(ContratoPatioFiltro filtro, Pageable pageable);
	
	ContratoPatioDTO consultarContratoPatioPeloId(Long id);
	
	void deletarContratoPatio(@Valid Long id);
	
    List<TipoServicoContratoDTO> listarTipoServicoContrato();
    
    List<DadosRelatorioPesquisaDTO> recuperaDadosRelatorio(ContratoPatioFiltro filtro);
    
}

