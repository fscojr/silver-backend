package br.gov.prf.silver.service;

import java.util.Optional;

import br.gov.prf.silver.domain.recolhimento.VeiculoAutoInfracao;

/**
 * Create by bruno.abreu.prestador on April/2020
 */

public interface MotorConsultaService {

	Optional<VeiculoAutoInfracao> consultaVeiculo(String chave, String campo);
    
}
