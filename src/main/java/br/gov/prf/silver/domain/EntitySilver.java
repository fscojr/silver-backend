package br.gov.prf.silver.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
@MappedSuperclass
public class EntitySilver implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "SIQUENCE")
    private Long id;

    @Column(name = "st_ativo")
    private Boolean ativo;
    
    @Column(name = "dh_inclusao")
    private LocalDateTime criadoEm;

    @Column(name = "dh_ultima_atualizacao")
    private LocalDateTime atualizadoEm;

    @Column(name = "dh_exclusao")
    private LocalDateTime excluidoEm;

    @Column(name = "ds_usuario")
    private String usuario;
    
}
