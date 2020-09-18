package br.gov.prf.silver.repository;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.recolhimento.TipoImagemVeiculo;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface TipoImagemVeiculoRepository extends JpaBaseRepository<TipoImagemVeiculo, Long> {
}
