package br.gov.prf.silver.service.dto;

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
public class TaPatioUnidadeDTO {

	private Long id;
	private PatioDTO patio;
	private Long unidade;
    private Long regional;
    
}
