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
import br.gov.prf.silver.domain.MotivoRecolhimento;
import br.gov.prf.silver.domain.Recolhimento;
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
@Table(name = "tb_motivo_recolhimento_auto", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MotivoRecolhimentoAuto implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_motivo_auto")
    @SequenceGenerator(name = "tb_motivo_recolhimento_auto_cd_seq_motivo_auto_seq", sequenceName = "tb_motivo_recolhimento_auto_cd_seq_motivo_auto_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_motivo_recolhimento_auto_cd_seq_motivo_auto_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_recolhimento")
    private Recolhimento recolhimento;

    @ManyToOne
    @JoinColumn(name = "fk_motivo_recolhimento")
    private MotivoRecolhimento motivoRecolhimento;

    @Column(name = "nu_auto_infracao")
    private String autoDeInfracao;

    @Column(name = "st_principal")
    private Boolean principal;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
