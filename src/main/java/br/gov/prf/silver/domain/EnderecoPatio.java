package br.gov.prf.silver.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "tb_endereco_patio", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EnderecoPatio implements Serializable {
	
	private static final long serialVersionUID = 1;

    @Id
    @SequenceGenerator(name="tb_endereco_patio_cd_seq_endereco_patio_seq", sequenceName="tb_endereco_patio_cd_seq_endereco_patio_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_endereco_patio_cd_seq_endereco_patio_seq")
    @Column(name = "cd_seq_endereco_patio", nullable = false)
    private Long id;

    @Column(name = "ds_endereco", length = 200)
    @Size(max = 200)
    private String endereco;

    @Column(name = "no_bairro", length = 100)
    @Size(max = 100)
    private String bairro;
    
    @Column(name = "nu_cep", length = 8 )
    @Size(max = 8)
    private String cep;
    
    @Column(name = "sg_uf")
    private String ufSigla;

    @Column(name = "fk_municipio")
    private Long municipio;

    @Column(name = "ds_referencia", length = 200)
    @Size(max = 200)
    private String referencia;

    @Column(name = "nu_telefone", length = 15)
    @Size(max = 15)
    private String telefone;
    
    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

    @Column(name = "dh_ultima_atualizacao")
    private LocalDateTime dhAtualizacao;
    
}
