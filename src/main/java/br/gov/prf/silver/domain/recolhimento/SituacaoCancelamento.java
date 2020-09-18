package br.gov.prf.silver.domain.recolhimento;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.gov.prf.silver.config.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_situacao_cancelamento", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SituacaoCancelamento {

    @Id
    @Column(name = "cd_situacao_cancelamento")
    private Long id;

    @Column(name = "ds_situacao_cancelamento")
    private String descricao;

    @Column(name = "st_ativo")
    private Boolean situacao;

    @Column(name = "dh_inclusao")
    private Instant dataInclusao;

}
