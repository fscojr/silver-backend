package br.gov.prf.silver.domain.recolhimento;

import br.gov.prf.silver.config.Constants;
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
@Table(name = "tb_imagem_veiculo", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ImagemVeiculo implements Serializable {

    private static final long serialVersionUID = 1;

    @Id
    @Column(name = "cd_seq_imagem_veiculo")
    @SequenceGenerator(name = "seq_imagem_veiculo_cd_seq_imagem_veiculo_seq", schema = "silver", sequenceName = "tb_imagem_veiculo_cd_seq_imagem_veiculo_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_imagem_veiculo_cd_seq_imagem_veiculo_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_estado_veiculo")
    private EstadoVeiculo estadoVeiculo;

    @ManyToOne
    @JoinColumn(name = "fk_tipo_imagem_veiculo")
    private TipoImagemVeiculo tipoImagemVeiculo;

    @Lob
    @Column(name = "ds_diretorio_imagem")
    private String arquivo;

    @Column(name = "ds_imagem")
    private String descricao;

    @Column(name = "ds_extensao_imagem")
    private String extensao;

    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
