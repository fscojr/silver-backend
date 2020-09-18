package br.gov.prf.silver.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.MotivoRecolhimentoHst;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface MotivoRecolhimentoHstRepository extends JpaBaseRepository<MotivoRecolhimentoHst, Long> {
	
	List<MotivoRecolhimentoHst> findByMotivoRecolhimentoId(Long id);
	
}
