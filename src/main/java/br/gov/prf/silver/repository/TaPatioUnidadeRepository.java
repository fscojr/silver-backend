package br.gov.prf.silver.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.TaPatioUnidade;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface TaPatioUnidadeRepository extends JpaBaseRepository<TaPatioUnidade, Long> {
	
	List<TaPatioUnidade> findAllByPatioId(Long idPatio);

	List<TaPatioUnidade> findAllByUnidade(Long idUnidade);
	
}
