package br.gov.prf.silver.domain.recolhimento;

import java.io.Serializable;
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
@Table(name = "tb_tipo_remocao", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoRemocao implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_tipo_remocao")
    private Long id;

    @Column(name = "ds_tipo_remocao")
    private String descricao;
    
    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
