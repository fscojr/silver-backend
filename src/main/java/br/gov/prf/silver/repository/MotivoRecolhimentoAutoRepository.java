package br.gov.prf.silver.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.MotivoRecolhimento;
import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.domain.recolhimento.MotivoRecolhimentoAuto;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface MotivoRecolhimentoAutoRepository extends JpaBaseRepository<MotivoRecolhimentoAuto, Long> {
	
	List<MotivoRecolhimentoAuto> findByMotivoRecolhimento(MotivoRecolhimento motivoRecolhimento);
	
	List<MotivoRecolhimentoAuto> findAllByMotivoRecolhimentoId(Long id);
	
	@Transactional(value = TxType.REQUIRES_NEW)
	MotivoRecolhimentoAuto findByAutoDeInfracaoAndMotivoRecolhimentoId(String autoInfracao, Long recolhimento);

	List<MotivoRecolhimentoAuto> findAllByRecolhimento(Optional<Recolhimento> recolhimento);

}
