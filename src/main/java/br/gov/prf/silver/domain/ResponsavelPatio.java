package br.gov.prf.silver.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

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
@Table(name = "tb_responsavel_patio", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ResponsavelPatio implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_responsavel_patio")
    @SequenceGenerator(name="tb_responsavel_patio_cd_seq_responsavel_patio_seq", sequenceName="tb_responsavel_patio_cd_seq_responsavel_patio_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "tb_responsavel_patio_cd_seq_responsavel_patio_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name="fk_patio")
    private Patio patio;

    @Column(name = "no_responsavel", length = 200)
    @Size(max = 200)
    private String nome;

    @Column(name = "ds_email_responsavel", length = 200)
    @Size(max = 200)
    private String email;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
