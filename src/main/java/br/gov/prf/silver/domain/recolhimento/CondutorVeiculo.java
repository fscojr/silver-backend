package br.gov.prf.silver.domain.recolhimento;

import java.io.Serializable;
import java.time.LocalDate;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.gov.prf.silver.config.Constants;
import br.gov.prf.silver.domain.Pais;
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
@Table(name = "tb_condutor_veiculo", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CondutorVeiculo implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_condutor_veiculo")
    @SequenceGenerator(name = "tb_condutor_veiculo_cd_seq_condutor_veiculo_seq", sequenceName="tb_condutor_veiculo_cd_seq_condutor_veiculo_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_condutor_veiculo_cd_seq_condutor_veiculo_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_tipo_condutor")
    private TipoCondutor tipo;

    @Column(name = "no_condutor")
    private String nome;

    @Column(name = "nu_cpf_condutor")
    private String cpf;

    @Column(name = "st_proprietario_veiculo")
    private Boolean proprietario;

    @ManyToOne
    @JoinColumn(name = "fk_tipo_documento_condutor")
    private TipoDocumentoCondutor tipoDocumento;

    @Column(name = "nu_documento_condutor")
    private String numeroDocumento;

    @Column(name = "nu_cnh_condutor")
    private String cnh;

    @Column(name = "tp_categoria_cnh")
    private Character categoriaCnh;

    @Column(name = "dt_validade_cnh")
    private LocalDate validadeCnh;

    @Transient
    @ManyToOne
    @JoinColumn(name = "fk_pais")
    private Pais pais;

    @Column(name = "sg_uf")
    private String ufSigla;

    @Column(name = "st_ativo")
    private Boolean ativo;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

    @Column(name = "ds_email_condutor")
    private String emailCondutor;
    
    @Column(name = "ds_telefone_condutor")
    private String telefoneCondutor;
    
}
