package br.gov.prf.silver.service;

import java.util.Optional;

import br.gov.prf.silver.domain.recolhimento.AutoInfracao;

/**
 * Create by bruno.abreu.prestador on April/2020
 */

public interface BarramentoService {

	Optional<AutoInfracao> consultaAutoInfracao(String autoInfracao);
    
}
