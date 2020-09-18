package br.gov.prf.silver.service.dto;

import java.time.LocalDate;
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
public class ContratoPatioDTO {

	private Long id;

    private PatioDTO patio;
	
    private ContratadaDTO contratada;

    private String processoSei;    

    private String numeroContrato;
    
    private LocalDate dtInicioVigencia;

    private LocalDate dtFimVigencia;

    private Boolean ativo;
    
    private LocalDateTime dhInclusao;

    private LocalDateTime dhAtualizacao;
    
    private Long regional;
    
    private Long unidade;
    
    //Para persistencencia
    private List<ServicoContratoDTO> servicoContrato;
    
    private List<TrechoCoberturaDTO> trechoCobertura;
    
    //
    private String regionalNome;
    
    private String unidadeNome;
}
