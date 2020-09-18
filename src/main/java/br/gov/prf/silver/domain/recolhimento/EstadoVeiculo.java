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
@Table(name = "tb_estado_veiculo", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EstadoVeiculo implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_estado_veiculo")
    @SequenceGenerator(name = "tb_estado_veiculo_cd_seq_estado_veiculo_seq", sequenceName = "tb_estado_veiculo_cd_seq_estado_veiculo_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_estado_veiculo_cd_seq_estado_veiculo_seq")
    private Long id;
    
    @Column(name = "nu_hodometro")
    private Long hodometro;
    
    @Column(name = "st_sem_hodometro")
    private Boolean possuiHodometro;

    @ManyToOne
    @JoinColumn(name = "fk_nivel_combustivel", nullable = true)
    private NivelCombustivel nivelCombustivel;

    @Column(name = "st_sem_marcador_combustivel")
    private Boolean semMarcadorCombustivel;

    @Column(name = "ds_estado_lataria")
    private String  estadoLataria;
    
    @Column(name = "st_dano_aparente_lataria")
    private Boolean possuiDanoAparenteLataria;
    
    @Column(name = "ds_estado_pintura")
    private String estadoPintura;

    @Column(name = "st_dano_aparante_pintura")
    private Boolean possuiDanoAparentePintura;

    @Column(name = "ds_estado_pneu")
    private String  estadoPneu;

    @Column(name = "st_dano_aparente_pneu")
    private Boolean possuiDanoAparentePneu;

    @Column(name = "ds_estado_som")
    private String estadoSom;

    @Column(name = "st_sem_aparelho_som")
    private Boolean semAparelhoDeSom;

    @Column(name = "ds_equipamento_faltante")
    private String equipamentoFaltante;

    @Column(name = "st_equipamento_regular")
    private Boolean equipamentoRegular;

    @Column(name = "ds_observacao")
    private String observacao;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;
    
    
}
