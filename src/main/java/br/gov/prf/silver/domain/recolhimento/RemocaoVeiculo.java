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
@Table(name = "tb_remocao_veiculo", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RemocaoVeiculo implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_remocao_veiculo")
    @SequenceGenerator(name = "tb_remocao_veiculo_cd_seq_remocao_veiculo_seq", sequenceName = "tb_remocao_veiculo_cd_seq_remocao_veiculo_seq",
        schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_remocao_veiculo_cd_seq_remocao_veiculo_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_tipo_remocao")
    private TipoRemocao tipoRemocao;

    @Column(name = "no_responsavel_remocao")
    private String responsavel;

    @Column(name = "nu_telefone_responsavel")
    private String telefoneResponsavel;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;
    
    @Column(name = "qt_total_km_percorrido")
    private Long quantidadeKmPercorrido;
    
    @Column(name = "qt_km_excendente_60km")
    private Long quantidadeKmExcedente;
    
}
