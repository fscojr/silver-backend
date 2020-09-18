package br.gov.prf.silver.service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RemocaoVeiculoDTO {

    private Long id;
    private TipoRemocaoDTO tipoRemocao;
    private String responsavel;
    private String telefoneResponsavel;
    private LocalDateTime dhInclusao;
    private Long quantidadeKmPercorrido;
    private Long quantidadeKmExcedente;
    private String dsJustificativaRemocao;

}
