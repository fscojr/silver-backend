package br.gov.prf.silver.repository;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.contratoPatio.TipoServicoContrato;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface TipoServicoContratoRepository extends JpaBaseRepository<TipoServicoContrato, Long> {
	
}
