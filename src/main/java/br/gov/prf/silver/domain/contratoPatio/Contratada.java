package br.gov.prf.silver.domain.contratoPatio;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.gov.prf.silver.config.Constants;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_contratada_contrato", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Contratada implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "cd_seq_contratada_contrato", nullable = false)
    @SequenceGenerator(name = "tb_contratada_contrato_cd_seq_contratada_contrato_seq", sequenceName = "tb_contratada_contrato_cd_seq_contratada_contrato_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_contratada_contrato_cd_seq_contratada_contrato_seq")
    private Long id;

    @Column(name = "nu_cpnj_contratada")
    private String cnpj;

    @Column(name = "no_contratada")
    private String nome;

    @Column(name = "st_ativo")
    private Boolean ativo;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
