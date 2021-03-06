package br.gov.prf.silver.domain.recolhimento;

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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.gov.prf.silver.config.Constants;
import br.gov.prf.silver.domain.Patio;
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
@Table(name = "tb_local_recolhimento", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class LocalRecolhimento implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_local_recolhimento")
    @SequenceGenerator(name = "tb_local_recolhimento_cd_seq_local_recolhimento_seq", sequenceName = "tb_local_recolhimento_cd_seq_local_recolhimento_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_local_recolhimento_cd_seq_local_recolhimento_seq")
    private Long id;
    
    @Column(name = "sg_uf")
    private String ufSigla;

    @Column(name = "nu_km_recolhimento")
    private Long km;

    @Column(name = "fk_br_recolhimento")
    private Long br;

    @Column(name = "fk_municipio")
    private Long municipio;

    @Column(name = "tp_sentido_trafego")
    private Long sentido;

    @Column(name = "fk_trecho")
    private Long trecho;

    @Column(name = "fk_unidade_organizacional")
    private Long unidade;

    @Column(name = "fk_regional")
    private Long regional;

    @ManyToOne
    @JoinColumn(name = "fk_patio")
    private Patio patio;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
