package br.gov.prf.silver.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.contratoPatio.ServicoContrato;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface ServicoContratoRepository extends JpaBaseRepository<ServicoContrato, Long> {
	
	List<ServicoContrato> findAllByContratoPatioId(Long id);

}
