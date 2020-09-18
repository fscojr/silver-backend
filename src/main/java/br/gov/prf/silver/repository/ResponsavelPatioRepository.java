package br.gov.prf.silver.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.ResponsavelPatio;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface ResponsavelPatioRepository extends JpaBaseRepository<ResponsavelPatio, Long> {
	
	List<ResponsavelPatio> findAllByPatioId(Long idPatio);
	
}
