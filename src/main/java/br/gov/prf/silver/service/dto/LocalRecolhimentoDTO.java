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
public class LocalRecolhimentoDTO {

    private Long id;
    private String ufSigla;
    private Long km;
    private Long br;
    private Long municipio;
    private Long sentido;
    private String sentidoSg;
    private Long trecho;
    private Long unidade;
    private Long regional;
    private PatioDTO patio;
    private LocalDateTime dhInclusao;

}
