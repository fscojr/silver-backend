package br.gov.prf.silver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.Veiculo;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Repository
public interface VeiculoRepository extends JpaBaseRepository<Veiculo, Long> {

    @Query(value = " SELECT v.*, r.* " +
		             " FROM silver.tb_recolhimento r " +
		        " LEFT JOIN silver.tb_veiculo v ON v.cd_seq_veiculo = r.fk_veiculo " +
		            " WHERE (v.nu_placa = :placa OR v.nu_chassi = :chassi)", nativeQuery = true)
    List<Veiculo> findByPlacaEqualsOrChassiEqualsWithRecolhimento(@Param("placa") String placa, @Param("chassi") String chassi);

}
