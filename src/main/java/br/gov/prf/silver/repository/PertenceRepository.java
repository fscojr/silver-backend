package br.gov.prf.silver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.Veiculo;
import br.gov.prf.silver.domain.recolhimento.Pertence;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface PertenceRepository extends JpaBaseRepository<Pertence, Long> {
	
	List<Pertence> findAllByVeiculo(Optional<Veiculo> veiculo);
	
}
