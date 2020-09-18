package br.gov.prf.silver.domain.contratoPatio;

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
import br.gov.prf.silver.domain.ContratoPatio;
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
@Table(name = "tb_trecho_cobertura", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TrechoCobertura implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cd_seq_trecho_cobertura", nullable = false)
    @SequenceGenerator(name = "tb_trecho_cobertura_cd_seq_trecho_cobertura_seq", sequenceName="tb_trecho_cobertura_cd_seq_trecho_cobertura_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "tb_trecho_cobertura_cd_seq_trecho_cobertura_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name="fk_contrato_patio")
    private ContratoPatio contratoPatio;

    @Column(name = "fk_rdv_rodovia")
    private Long rodovia;

    @Column(name = "nu_km_inicial", length = 4)
    private Long kmInicial;

    @Column(name = "nu_km_final", length = 4)
    private Long kmFinal;

    @Column(name = "st_ativo")
    private Boolean ativo;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

    @Column(name = "dh_ultima_atualizacao")
    private LocalDateTime dhAtualizacao;

}
