package br.gov.prf.silver.repository;

import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.MotivoRecolhimento;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface MotivoRecolhimentoRepository extends JpaBaseRepository<MotivoRecolhimento, Long> {

	@Transactional(value = TxType.REQUIRES_NEW)
    MotivoRecolhimento findByAmparoLegalContainingIgnoreCase(String amparo);
    
    List<MotivoRecolhimento> findAllByMotivoId(Long id);
    
    MotivoRecolhimento findByMotivoIdAndAmparoLegal(Long id, String amparoLegal);

}
