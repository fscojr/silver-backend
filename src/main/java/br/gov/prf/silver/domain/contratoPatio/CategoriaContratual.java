package br.gov.prf.silver.domain.contratoPatio;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "tb_categoria_contratual", schema = Constants.SCHEMA_PGADMIN_SILVER)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CategoriaContratual {

    @Id
    @Column(name = "cd_categoria_contratual")
    private Long   id;

    @Column(name = "ds_categoria_contratual")
    private String categoria;
    
    @Column(name = "st_ativo")
    private Boolean ativo;
    
    @Column(name = "dh_inclusao")
    private LocalDateTime dhInclusao;

}
