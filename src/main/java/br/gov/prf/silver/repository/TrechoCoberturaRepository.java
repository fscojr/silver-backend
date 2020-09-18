package br.gov.prf.silver.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.contratoPatio.TrechoCobertura;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface TrechoCoberturaRepository extends JpaBaseRepository<TrechoCobertura, Long> {
	
	List<TrechoCobertura> findAllByContratoPatioId(Long id);
	
}
