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
@Table(name = "tb_situacao_solicitacao", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SituacaoSolicitacao implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_situacao_solicitacao")
    @SequenceGenerator(name = "tb_situacao_solicitacao_cd_seq_situacao_solicitacao_seq", sequenceName = "tb_situacao_solicitacao_cd_seq_situacao_solicitacao_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_situacao_solicitacao_cd_seq_situacao_solicitacao_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_recolhimento")
    private Recolhimento recolhimento;

    @ManyToOne
    @JoinColumn(name = "fk_solicitacao_cancelamento")
    private SolicitacaoCancelamento solicitacao;
    
    @ManyToOne
    @JoinColumn(name = "fk_situacao_cancelamento")
    private SituacaoCancelamento situacao;
    
    @Column(name = "ds_justificativa")
    private String justificativa;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

    @Column(name = "ds_usuario_inclusao")
    private String usuarioInclusao;

}
