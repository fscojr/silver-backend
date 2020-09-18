package br.gov.prf.silver.service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ImagemVeiculoDTO {

    private Long id;
    private EstadoVeiculoDTO estadoVeiculo;
    private TipoImagemVeiculoDTO tipo;
    private String arquivo;
    private String extensao;
    private LocalDateTime dhInclusao;

}
