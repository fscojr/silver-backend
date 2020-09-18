package br.gov.prf.silver.domain.recolhimento;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_nivel_combustivel", schema = "silver")
public class NivelCombustivel {

    @Id
    @Column(name = "cd_nivel_combustivel")
    private Long id;

    @Column(name = "no_nivel_combustivel")
    private String nome;

    @Column(name = "ds_nivel_combustivel")
    private String descricao;

    @Column(name = "dh_inclusao")
    private LocalDateTime dtInclusao;
    
}
