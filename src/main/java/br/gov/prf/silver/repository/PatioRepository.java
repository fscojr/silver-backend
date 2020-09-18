package br.gov.prf.silver.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.Patio;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface PatioRepository extends JpaBaseRepository<Patio, Long> {
	
	List<Patio> findAllByNomeContainingIgnoreCase(String nomePatio);

}
