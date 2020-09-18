package br.gov.prf.silver.domain;

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
@Table(name = "tb_motivo", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Motivo implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_motivo")
    private Long id;

    @Column(name = "sg_motivo")
    private String sigla;

    @Column(name = "ds_motivo")
    private String descricao;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
