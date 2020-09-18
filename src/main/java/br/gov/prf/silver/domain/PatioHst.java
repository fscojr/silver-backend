package br.gov.prf.silver.domain;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

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
@Table(name = "th_patio", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PatioHst implements Serializable {

	private static final long serialVersionUID = 1;
	
    @Id
    @Column(name = "cd_seq_hst_patio", nullable = false)
    @SequenceGenerator(name="th_patio_cd_seq_hst_patio_seq", sequenceName="th_patio_cd_seq_hst_patio_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "th_patio_cd_seq_hst_patio_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_patio")
    private Patio patio;
    
    @Column(name = "no_patio", length = 200)
    @Size(max = 200)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "fk_tipo_patio", referencedColumnName = "cd_tipo_patio")
    private TipoPatio tipoPatio;
    
    @ManyToOne
    @JoinColumn(name = "fk_endereco_patio", referencedColumnName = "cd_seq_endereco_patio")
    private EnderecoPatio enderecoPatio;

    @Column(name = "st_ativo")
    private Boolean ativo;

    @Column(name = "nu_area_patio", length = 10)
    private Long metragem;
    
    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

    @OneToMany(mappedBy="patio")
    private List<TaPatioUnidade> patioUnidadeList;
    
    @OneToMany(mappedBy="patio")
    private List<ResponsavelPatio> responsavelPatioList;
    
    @Column(name = "nu_cpf_responsavel")
    private String cpfResponsavel;

}
