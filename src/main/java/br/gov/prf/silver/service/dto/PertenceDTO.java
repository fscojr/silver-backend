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
public class PertenceDTO {

    private Long id;
    private VeiculoDTO veiculo;
    private CombinacaoDTO combinacao;
    private TipoPertenceDTO tipoPertence;
    private String descricao;
    private LocalDateTime dhInclusao;
    
}
