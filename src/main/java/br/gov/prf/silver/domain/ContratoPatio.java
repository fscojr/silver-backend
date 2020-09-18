package br.gov.prf.silver.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.gov.prf.silver.config.Constants;
import br.gov.prf.silver.domain.contratoPatio.Contratada;
import br.gov.prf.silver.domain.contratoPatio.ServicoContrato;
import br.gov.prf.silver.domain.contratoPatio.TrechoCobertura;
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
@Table(name = "tb_contrato_patio", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ContratoPatio implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "cd_seq_contrato_patio", nullable = false)
    @SequenceGenerator(name = "tb_contrato_patio_cd_seq_contrato_patio_seq", sequenceName = "tb_contrato_patio_cd_seq_contrato_patio_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "tb_contrato_patio_cd_seq_contrato_patio_seq")
    private Long id;

	@ManyToOne
    @JoinColumn(name="fk_patio")
    private Patio patio;
	
    @OneToOne
    @JoinColumn(name="fk_contratada_contrato")
    private Contratada contratada;

    @Column(name = "nu_processo_sei")
    private String processoSei;    

    @Column(name = "nu_contrato")
    private String numeroContrato;
    
    @Column(name = "dt_inicio_vigencia")
    private LocalDate dtInicioVigencia;

    @Column(name = "dt_fim_vigencia")
    private LocalDate dtFimVigencia;

    @Column(name = "st_ativo")
    private Boolean ativo;
    
    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

    @Column(name = "dh_ultima_atualizacao")
    private LocalDateTime dhAtualizacao;

    @Column(name = "fk_regional")
    private Long regional;

    @Column(name = "fk_unidade_organizacional")
    private Long unidade;
    
    @OneToMany(mappedBy="contratoPatio")
    private List<ServicoContrato> servicoContratroList;
    
    @OneToMany(mappedBy="contratoPatio")
    private List<TrechoCobertura> trechoCoberturaList;
    
}
