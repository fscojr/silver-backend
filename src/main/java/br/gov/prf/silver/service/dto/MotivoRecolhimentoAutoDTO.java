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
public class MotivoRecolhimentoAutoDTO {

    private Long id;

    private RecolhimentoDTO recolhimento;

    private MotivoRecolhimentoDTO motivoRecolhimento;

    private String autoDeInfracao;

    private Boolean principal;

    private LocalDateTime dhInclusao;
    
}
