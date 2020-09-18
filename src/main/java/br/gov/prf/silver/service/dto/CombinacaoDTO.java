package br.gov.prf.silver.service.dto;

import java.time.LocalDateTime;
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
public class CombinacaoDTO {

	private Long id;
	private VeiculoDTO veiculo;
    private EstadoVeiculoDTO estadoVeiculo;
    private List<PertenceDTO> pertenceList;
    private String placa;
    private String chassi;
    private Boolean regular;
    private String observacao;
    private LocalDateTime dhInclusao;
    
}
