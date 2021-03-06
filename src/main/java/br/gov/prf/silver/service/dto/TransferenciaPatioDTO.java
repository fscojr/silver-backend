package br.gov.prf.silver.service.dto;

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
public class TransferenciaPatioDTO {

    private Long id;
    private List<RecolhimentoDTO> recolhimento;
    private String justificativa;
    
}
