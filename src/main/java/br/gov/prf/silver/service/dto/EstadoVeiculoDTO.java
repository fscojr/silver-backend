package br.gov.prf.silver.service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EstadoVeiculoDTO {

    private Long id;
    private Long hodometro;
    private Boolean possuiHodometro;
    private NivelCombustivelDTO nivelCombustivel;
    private Boolean semMarcadorCombustivel;
    private String estadoLataria;
    private Boolean possuiDanoAparenteLataria;
    private String estadoPintura;
    private Boolean possuiDanoAparentePintura;
    private String estadoPneu;
    private Boolean possuiDanoAparentePneu;
    private String estadoSom;
    private Boolean semAparelhoDeSom;
    private String equipamentoFaltante;
    private Boolean equipamentoRegular;
    private String observacao;
    private LocalDateTime dhInclusao;

}
