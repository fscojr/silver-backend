package br.gov.prf.silver.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.recolhimento.EmplacamentoVeiculo;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface EmplacamentoVeiculoRepository extends JpaBaseRepository<EmplacamentoVeiculo, Long> {

	Optional<EmplacamentoVeiculo> findByDescricaoContainingIgnoreCase(String descricao);

}
