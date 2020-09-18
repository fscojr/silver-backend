package br.gov.prf.silver.service.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContratadaDTO {

	private Long id;

	private String cnpj;

    private String nome;

    private Boolean ativa;

    private LocalDateTime dhInclusao;
    
    public ContratadaDTO(Long id, String cnpj, String nome) {
    	this.id = id;
    	this.cnpj = cnpj;
    	this.nome = nome;
    }
    
}
