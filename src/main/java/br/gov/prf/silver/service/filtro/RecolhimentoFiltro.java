package br.gov.prf.silver.service.filtro;

import java.time.LocalDate;
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
public class RecolhimentoFiltro {

    private String numeroRecolhimento;
    private String placa;
    private String chassi;
    private String renavam;
    private String cpfCondutor;
    private String nomeCondutor;
    private Long regional;
    private Long unidade;
    private Long patio;
    private String autoInfracao;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private String prfResponsavel;
    private String observacao;
    private List<Long> situacao;
    
}
