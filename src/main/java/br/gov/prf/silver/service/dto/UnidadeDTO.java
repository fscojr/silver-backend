package br.gov.prf.silver.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeDTO {

    private Long id;
    private Long idUnidade;
    private String nome;
    private String sigla;
    private String nomePai;
    private Long unidadePai;
    
}
