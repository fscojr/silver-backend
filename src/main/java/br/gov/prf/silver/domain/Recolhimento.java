package br.gov.prf.silver.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.gov.prf.silver.config.Constants;
import br.gov.prf.silver.domain.recolhimento.CondutorVeiculo;
import br.gov.prf.silver.domain.recolhimento.LocalRecolhimento;
import br.gov.prf.silver.domain.recolhimento.MotivoRecolhimentoAuto;
import br.gov.prf.silver.domain.recolhimento.PolicialRecolhimento;
import br.gov.prf.silver.domain.recolhimento.RemocaoVeiculo;
import br.gov.prf.silver.domain.recolhimento.SituacaoRecolhimento;
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
@Table(name = "tb_recolhimento", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Recolhimento implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_recolhimento")
    @SequenceGenerator(name = "seq_gen_recolhimento", schema = "silver", sequenceName = "tb_recolhimento_cd_seq_recolhimento_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen_recolhimento")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_veiculo")
    private Veiculo veiculo;

    @ManyToOne
    @JoinColumn(name = "fk_local_recolhimento", nullable = true)
    private LocalRecolhimento localRecolhimento;

    @ManyToOne
    @JoinColumn(name = "fk_remocao_veiculo", nullable = true)
    private RemocaoVeiculo remocaoVeiculo;

    @ManyToOne
    @JoinColumn(name = "fk_condutor_veiculo")
    private CondutorVeiculo condutorVeiculo;

    @Column(name = "ds_providencias_restituicao")
    private String providenciasRestituicao;

    @Column(name = "dt_recolhimento")
    private LocalDate dataRecolhimento;

    @Column(name = "hr_recolhimento")
    private LocalTime horaRecolhimento;

    @Column(name = "nu_sequencia")
    private Long sequencia;

    @ManyToOne
    @JoinColumn(name = "fk_situacao_recolhimento")
    private SituacaoRecolhimento situacao;

    @Column(name = "nu_drv")
    private String drv;
    
    @Column(name = "ds_justificativa_remocao")
    private String justificativaRemocao;
    
    @OneToMany
    @JoinTable(
        schema = Constants.SCHEMA_PGADMIN_SILVER,
        name = "tb_motivo_recolhimento_auto",
        joinColumns = {@JoinColumn(name = "fk_recolhimento")},
        inverseJoinColumns = {@JoinColumn(name = "cd_seq_motivo_auto")})
    private List<MotivoRecolhimentoAuto> motivoAutoList;
    
    @OneToMany
    @JoinTable(
        schema = Constants.SCHEMA_PGADMIN_SILVER,
        name = "tb_policial_recolhimento",
        joinColumns = {@JoinColumn(name = "fk_recolhimento")},
        inverseJoinColumns = {@JoinColumn(name = "cd_seq_policial_recolhimento")})
    private List<PolicialRecolhimento> prfResponsavel;

}
