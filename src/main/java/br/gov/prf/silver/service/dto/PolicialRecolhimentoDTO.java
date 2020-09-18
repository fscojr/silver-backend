package br.gov.prf.silver.service.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PolicialRecolhimentoDTO {

    private Long id;
    private RecolhimentoDTO recolhimento;
    private String matricula;
    private String nome;
    private String cpf;
    private String email;
    private Boolean ativo;
    private Character tipo;
    private LocalDateTime dhInclusao;

}
