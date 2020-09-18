package br.gov.prf.silver.service.dto;

import java.time.LocalDate;
import java.time.LocalTime;
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
public class RecolhimentoDTO {

    private Long id;
    private LocalDate dataRecolhimento;
    private LocalTime horaRecolhimento;
    private String providenciasRestituicao;
    private VeiculoDTO veiculo;
    private List<ImagemVeiculoDTO> imagensEstado;
    private CondutorVeiculoDTO condutorVeiculo;
    private LocalRecolhimentoDTO localRecolhimento;
    private RemocaoVeiculoDTO remocaoVeiculo;
    private Long sequencia;
    private SituacaoDTO situacao;
    private String drv;
    private PolicialRecolhimentoDTO responsavel;   
    
    //usado na hora de salvar o recolhimento
    private List<CombinacaoDTO> combinacaoList;
    private List<PertenceDTO> pertenceList;
    private List<MotivoRecolhimentoAutoDTO> motivos;
    private List<PolicialRecolhimentoDTO> prfAuxiliares;
    
}
