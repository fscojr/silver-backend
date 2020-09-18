package br.gov.prf.silver.repository;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.recolhimento.TipoCondutor;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface TipoCondutorRepository extends JpaBaseRepository<TipoCondutor, Long> {
}
