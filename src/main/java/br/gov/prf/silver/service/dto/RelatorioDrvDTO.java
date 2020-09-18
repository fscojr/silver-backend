package br.gov.prf.silver.service.dto;

import java.util.List;

import br.gov.prf.silver.domain.recolhimento.VeiculoAutoInfracao;
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
public class RelatorioDrvDTO {
	
	private String regional;
	private RecolhimentoDTO recolhimento;
	private VeiculoAutoInfracao veiculo;
	private List<CombinacaoDTO> combinacaoList;
	private CondutorVeiculoDTO condutor;
	private LocalRecolhimentoDTO localRecolhimento;
	private String municipioLocalRecolhimento;
    private List<MotivoRecolhimentoDTO> motivoList;
    private PatioDTO patio;
    private String municipioEnderecoPatio;
    private RemocaoVeiculoDTO remocao;
    private List<PertenceDTO> pertenceList;
    private EstadoVeiculoDTO estado;
    private PolicialRecolhimentoDTO policial;

}
