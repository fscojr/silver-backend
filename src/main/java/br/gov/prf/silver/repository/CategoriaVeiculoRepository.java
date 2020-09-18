package br.gov.prf.silver.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.recolhimento.CategoriaVeiculo;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface CategoriaVeiculoRepository extends JpaBaseRepository<CategoriaVeiculo, Long> {

	Optional<CategoriaVeiculo> findByDescricaoContainingIgnoreCase(String descricao);

}
