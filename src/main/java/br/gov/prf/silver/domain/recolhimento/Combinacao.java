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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.gov.prf.silver.config.Constants;
import br.gov.prf.silver.domain.Veiculo;
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
@Table(name = "tb_combinacao", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Combinacao implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_combinacao")
    @SequenceGenerator(name="tb_combinacao_cd_seq_combinacao_seq", sequenceName="tb_combinacao_cd_seq_combinacao_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "tb_combinacao_cd_seq_combinacao_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_veiculo")
    private Veiculo veiculo;

    @OneToOne
    @JoinColumn(name = "fk_estado_combinacao")
    private EstadoVeiculo estadoVeiculo;

    @Column(name = "nu_placa")
    private String placa;

    @Column(name = "nu_chassi")
    private String chassi;

    @Column(name = "st_combinacao_regular")
    private Boolean regular;

    @Column(name = "ds_observacao")
    private String observacao;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
