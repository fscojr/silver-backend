package br.gov.prf.silver.repository;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.contratoPatio.DescricaoServico;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface DescricaoServicoRepository extends JpaBaseRepository<DescricaoServico, Long> {
	
}
