package br.gov.prf.silver.domain.contratoPatio;

import java.time.LocalDateTime;

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
@Table(name = "tb_tipo_servico_contrato", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoServicoContrato {

	@Id
    @Column(name = "cd_tipo_servico_contrato")
    private Long id;

    @Column(name = "ds_tipo_servico_contrato")
    private String descricao;
    
    @Column(name = "st_ativo")
    private Boolean ativo;

    @Column(name = "dh_inclusao")
    private LocalDateTime dtInclusao;

    @Column(name = "dh_ultima_atualizacao")
    private LocalDateTime dtAtualizacao;

}
