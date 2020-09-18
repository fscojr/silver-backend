package br.gov.prf.silver.domain.recolhimento;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "tb_solicitacao_cancelamento", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SolicitacaoCancelamento implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_solicitacao_cancelamento")
    @SequenceGenerator(name="seq_gen_solicitacao_cancelamento", schema = "silver", sequenceName="tb_solicitacao_cancelamento_cd_seq_solicitacao_cancelamento_seq", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_gen_solicitacao_cancelamento")
    private Long id;
    
    @Column(name = "nu_cpf_solicitante")
    private String cpfSolicitante;

    @Column(name = "no_solicitante")
    private String nomeSolicitante;

    @Column(name = "ds_solicitacao_cancelamento")
    private String descricaoSolicitacao;

    @Column(name = "ds_motivo_cancelamento")
    private String descricaoMotivo;

    @Column(name = "dh_solicitacao")
    private LocalDateTime dhSolicitacao;

}
