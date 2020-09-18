package br.gov.prf.silver.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.PatioHst;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface PatioHstRepository extends JpaBaseRepository<PatioHst, Long> {
	
	List<PatioHst> findByPatioId(Long id);
}
