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
@Table(name = "tb_pertence", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Pertence implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_pertence")
    @SequenceGenerator(name="seq_gen_pertence_cd_seq_pertence_seq", schema = "silver", 
    		sequenceName="tb_pertence_cd_seq_pertence_seq", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_gen_pertence_cd_seq_pertence_seq")
    private Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_veiculo")
    private Veiculo veiculo;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "fk_combinacao")
    private Combinacao combinacao;
    
    @ManyToOne
    @JoinColumn(name = "fk_tipo_pertence")
    private TipoPertence tipoPertence;

    @Column(name = "ds_pertence")
    private String descricao;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
