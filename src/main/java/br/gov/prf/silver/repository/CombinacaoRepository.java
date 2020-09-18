package br.gov.prf.silver.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.recolhimento.Combinacao;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface CombinacaoRepository extends JpaBaseRepository<Combinacao, Long> {
	
	List<Combinacao> findByVeiculoId(Long id);
	
}
