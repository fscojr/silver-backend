package br.gov.prf.silver.service.filtro;

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
public class PatioFiltro {

    private String nome;
    private Long tipoPatio;
    private Long regional;
    private Long unidade;
    private String uf;
    private Long municipio;
    private Boolean ativo;
    private String nomeResponsavel;
    
}
