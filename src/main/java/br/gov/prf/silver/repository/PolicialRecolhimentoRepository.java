package br.gov.prf.silver.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.recolhimento.PolicialRecolhimento;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface PolicialRecolhimentoRepository extends JpaBaseRepository<PolicialRecolhimento, Long> {
	
	List<PolicialRecolhimento> findByRecolhimentoId(Long id);
	
}
