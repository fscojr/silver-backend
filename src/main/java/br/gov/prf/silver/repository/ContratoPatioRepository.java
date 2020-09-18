package br.gov.prf.silver.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.ContratoPatio;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface ContratoPatioRepository extends JpaBaseRepository<ContratoPatio, Long> {
	
	List<ContratoPatio> findAllByPatioId(Long idPatio);
	
}
