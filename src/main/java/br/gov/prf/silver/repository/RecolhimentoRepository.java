package br.gov.prf.silver.repository;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.Recolhimento;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
@Repository
public interface RecolhimentoRepository extends JpaBaseRepository<Recolhimento, Long> {

    List<Recolhimento> findByDataRecolhimentoBetweenAndSequenciaIsGreaterThanEqualOrderBySequenciaDesc(LocalDate data_start, LocalDate data_end, Long sequencia);

	@Transactional(value = TxType.REQUIRES_NEW)
    @Query("SELECT max(r.sequencia) FROM Recolhimento r where (r.sequencia >= 500 and r.sequencia <= 999)")
    Long maxSequencia();
    
}
