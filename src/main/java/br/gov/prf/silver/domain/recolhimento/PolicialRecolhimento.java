package br.gov.prf.silver.domain.recolhimento;

import br.gov.prf.silver.config.Constants;
import br.gov.prf.silver.domain.Recolhimento;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "tb_policial_recolhimento", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PolicialRecolhimento implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_policial_recolhimento")
    @SequenceGenerator(name = "tb_policial_recolhimento_cd_seq_policial_recolhimento_seq", sequenceName = "tb_policial_recolhimento_cd_seq_policial_recolhimento_seq",
        schema = Constants.SCHEMA_PGADMIN_SILVER, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_policial_recolhimento_cd_seq_policial_recolhimento_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_recolhimento")
    private Recolhimento recolhimento;

    @Column(name = "no_policial")
    private String nome;

    @Column(name = "nu_matricula")
    private String matricula;

    @Column(name = "nu_cpf_policial")
    private String cpf;

    @Column(name = "st_ativo")
    private Boolean ativo;

    @Column(name = "tp_policial")
    private Character tipo;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
