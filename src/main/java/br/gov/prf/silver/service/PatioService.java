package br.gov.prf.silver.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.prf.silver.service.dto.PatioDTO;
import br.gov.prf.silver.service.dto.dominio.TipoPatioDTO;
import br.gov.prf.silver.service.filtro.PatioFiltro;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface PatioService {
	
	PatioDTO salvarPatio(@Valid PatioDTO dto);
	
	Page<PatioDTO> consultarPatio(PatioFiltro filtro, Pageable pageable);
	
	PatioDTO consultarPatioPorId(Long id);

	void deletarPatio(@Valid Long id);
	
	List<TipoPatioDTO> listarTiposPatio();
	
	List<PatioDTO> consultarPatioPorUnidade(Long idUnidade);
	
}
