package br.gov.prf.silver.repository;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.recolhimento.MarcaVeiculo;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface MarcaVeiculoRepository extends JpaBaseRepository<MarcaVeiculo, Long> {

	MarcaVeiculo findByDescricaoContainingIgnoreCase(String descricao);

}
