package br.gov.prf.silver.service.filtro;

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
public class MonitorSolicitacaoFiltro {

    private String numeroRecolhimento;
    private String placa;
    private String chassi;
    private String renavam;
    private String policial;
    private LocalDateTime dataInicial;
    private LocalDateTime dataFinal;
    private String descricao;
    private Long tipoSolicitacao;
    
}
