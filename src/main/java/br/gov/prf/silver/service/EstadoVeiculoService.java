package br.gov.prf.silver.service;

import java.util.List;

import javax.validation.Valid;

import br.gov.prf.silver.service.dto.EstadoVeiculoDTO;
import br.gov.prf.silver.service.dto.NivelCombustivelDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface EstadoVeiculoService {

	EstadoVeiculoDTO salvar(@Valid EstadoVeiculoDTO dto);

	EstadoVeiculoDTO consultarPorId(Long id);

	List<NivelCombustivelDTO> recuperarListaNiveisCombustivel();
	
}
