package br.gov.prf.silver.domain.contratoPatio;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.Size;

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
@Table(name = "tb_servico_contrato", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ServicoContrato implements Serializable {

	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "cd_seq_servico_contrato", nullable = false)
    @SequenceGenerator(name = "tb_servico_contrato_cd_seq_servico_contrato_seq", sequenceName = "tb_servico_contrato_cd_seq_servico_contrato_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "tb_servico_contrato_cd_seq_servico_contrato_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name="fk_contrato_patio")
    private ContratoPatio contratoPatio;

    @ManyToOne
    @JoinColumn(name="fk_tipo_servico_contrato")
    private TipoServicoContrato tipoServico;

    @ManyToOne
    @JoinColumn(name="fk_categoria_contratual")
    private CategoriaContratual categoriaContratual;

    @ManyToOne
    @JoinColumn(name="fk_descricao_servico")
    private DescricaoServico descricaoServico;

    @Column(name = "ds_outra_descricao", length = 50)
    @Size(max = 50)
    private String outraDescricao;

    @Column(name = "no_unidade", length = 15)
    @Size(max = 15)
    private String unidade;

    @Column(name = "vl_servico", length = 10)
    private BigDecimal valor;

    @Column(name = "st_ativo")
    private Boolean ativo;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

    @Column(name = "dh_ultima_atualizacao")
    private LocalDateTime dhAtualizacao;

}
