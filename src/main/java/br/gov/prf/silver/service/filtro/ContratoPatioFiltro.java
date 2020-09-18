package br.gov.prf.silver.service.filtro;

import java.util.List;

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
public class ContratoPatioFiltro {

	private String processoSei;    
    private String contratada;
    private String numeroContrato;
    private Long patio;
    private Boolean vigencia;
    private List<Long> servico;
    private Long unidade;
    private Long regional;
    
}
