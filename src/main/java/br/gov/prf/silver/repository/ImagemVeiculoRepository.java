package br.gov.prf.silver.repository;

import br.gov.prf.silver.domain.recolhimento.ImagemVeiculo;
import org.springframework.stereotype.Repository;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
@Repository
public interface ImagemVeiculoRepository extends JpaBaseRepository<ImagemVeiculo, Long> {
}
