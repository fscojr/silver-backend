package br.gov.prf.silver.domain;

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
@Table(name = "tb_motivo_recolhimento", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class MotivoRecolhimento implements Serializable {

	private static final long serialVersionUID = 1;
	
    @Id
    @Column(name = "cd_seq_motivo_recolhimento")
    @SequenceGenerator(name = "tb_motivo_recolhimento_cd_seq_motivo_recolhimento_seq", sequenceName = "tb_motivo_recolhimento_cd_seq_motivo_recolhimento_seq", 
    		schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_motivo_recolhimento_cd_seq_motivo_recolhimento_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_motivo")
    private Motivo motivo;

    @Column(name = "ds_outro_motivo", length = 30)
    @Size(max = 30)
    private String outroMotivo;

    @Column(name = "ds_amparo_legal", length = 30)
    @Size(max = 30)
    private String amparoLegal;

    @Column(name = "nu_ano", length = 4)
    private Long ano;

    @Column(name = "dt_publicacao", length = 10)
    private LocalDate dataPublicacao;
    
    @Column(name = "tp_documento")
    private String tipoDocumento;
    
    @Column(name = "st_ativo")
    private Boolean ativo;
    
    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
