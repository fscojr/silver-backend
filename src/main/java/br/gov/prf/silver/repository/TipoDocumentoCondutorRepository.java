package br.gov.prf.silver.repository;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.recolhimento.TipoDocumentoCondutor;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface TipoDocumentoCondutorRepository extends JpaBaseRepository<TipoDocumentoCondutor, Long> {
}
