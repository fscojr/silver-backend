package br.gov.prf.silver.repository;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.recolhimento.CorVeiculo;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface CorVeiculoRepository extends JpaBaseRepository<CorVeiculo, Long> {

    CorVeiculo findByDescricaoContainingIgnoreCase(String descricao);

}
