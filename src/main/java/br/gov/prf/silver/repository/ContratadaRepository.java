package br.gov.prf.silver.repository;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.contratoPatio.Contratada;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface ContratadaRepository extends JpaBaseRepository<Contratada, Long> {

	Contratada findByCnpj(String cnpj);
    
}
