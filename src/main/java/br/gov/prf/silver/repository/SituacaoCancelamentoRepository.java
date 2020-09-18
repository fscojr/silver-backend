package br.gov.prf.silver.repository;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.recolhimento.SituacaoCancelamento;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface SituacaoCancelamentoRepository extends JpaBaseRepository<SituacaoCancelamento, Long> {
}
