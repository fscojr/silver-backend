package br.gov.prf.silver.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
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
@Table(name = "ta_patio_und_administrativa", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TaPatioUnidade implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_patio_und_administrativa")
    @SequenceGenerator(name = "ta_patio_und_administrativa_cd_seq_patio_und_administrativa_seq", sequenceName = "ta_patio_und_administrativa_cd_seq_patio_und_administrativa_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ta_patio_und_administrativa_cd_seq_patio_und_administrativa_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_patio")
    private Patio patio;

    @Column(name = "fk_unidade_administrativa")
    private Long unidade;

    @Column(name = "fk_regional")
    private Long regional;
    
}
