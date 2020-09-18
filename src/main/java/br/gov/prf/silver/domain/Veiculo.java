package br.gov.prf.silver.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import br.gov.prf.silver.config.Constants;
import br.gov.prf.silver.domain.recolhimento.CategoriaVeiculo;
import br.gov.prf.silver.domain.recolhimento.Combinacao;
import br.gov.prf.silver.domain.recolhimento.CorVeiculo;
import br.gov.prf.silver.domain.recolhimento.EmplacamentoVeiculo;
import br.gov.prf.silver.domain.recolhimento.EspecieVeiculo;
import br.gov.prf.silver.domain.recolhimento.EstadoVeiculo;
import br.gov.prf.silver.domain.recolhimento.MarcaVeiculo;
import br.gov.prf.silver.domain.recolhimento.Pertence;
import br.gov.prf.silver.domain.recolhimento.TipoVeiculo;
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
@Table(name = "tb_veiculo", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Veiculo implements Serializable {

	private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_veiculo")
    @SequenceGenerator(name="seq_gen_tb_veiculo_cd_seq_veiculo_seq", schema = "silver", sequenceName="tb_veiculo_cd_seq_veiculo_seq", initialValue=1, allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "seq_gen_tb_veiculo_cd_seq_veiculo_seq")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "fk_tipo_veiculo")
    private TipoVeiculo tipoVeiculo;
    
    @ManyToOne
    @JoinColumn(name = "fk_estado_veiculo", nullable = true)
    private EstadoVeiculo estadoVeiculo;
    
    @ManyToOne
    @JoinColumn(name = "fk_emplacamento")
    private EmplacamentoVeiculo emplacamento;
    
    @Column(name = "nu_placa")
    private String placa;
    
    @Column(name = "nu_chassi")
    private String chassi;
    
    @Column(name = "nu_renavam")
    private String renavam;
    
    @ManyToOne
    @JoinColumn(name = "fk_categoria_veiculo")
    private CategoriaVeiculo categoriaVeiculo;
    
    @ManyToOne
    @JoinColumn(name = "fk_marca_veiculo")
    private MarcaVeiculo marcaVeiculo;
    
    @Column(name = "ds_outra_marca")
    private String outraMarca;
    
    @Column(name = "ds_modelo_veiculo")
    private String modelo;
    
    @ManyToOne
    @JoinColumn(name = "fk_cor_veiculo")
    private CorVeiculo corVeiculo;
    
    @ManyToOne
    @JoinColumn(name = "fk_especie_veiculo")
    private EspecieVeiculo especieVeiculo;

    @Column(name = "sg_uf")
    private String ufSigla;

    @ManyToOne
    @JoinColumn(name = "fk_pais")
    private Pais pais;

    @Column(name = "no_proprietario")
    private String nomeProprietario;
    
    @Column(name = "nu_cpf_cnpj_proprietario")
    private String cpfCnpjProprietario;

    @Column(name = "st_ativo")
    private Boolean ativo;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;
    
    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Combinacao> combinacao = new ArrayList<>();

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pertence> pertence = new ArrayList<>();
}
