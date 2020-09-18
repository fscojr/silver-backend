package br.gov.prf.silver.repository;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.recolhimento.EspecieVeiculo;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface EspecieVeiculoRepository extends JpaBaseRepository<EspecieVeiculo, Long> {

    EspecieVeiculo findByDescricaoContainingIgnoreCase(String descricao);

}
