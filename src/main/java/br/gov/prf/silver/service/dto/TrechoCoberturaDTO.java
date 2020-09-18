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
public class TrechoCoberturaDTO {

	private Long id;

	private ContratoPatioDTO contratoPatio;

    private Long rodovia;

    private Long kmInicial;

    private Long kmFinal;

    private Boolean ativo;

    private LocalDateTime dhInclusao;

    private LocalDateTime dhAtualizacao;
    
}
