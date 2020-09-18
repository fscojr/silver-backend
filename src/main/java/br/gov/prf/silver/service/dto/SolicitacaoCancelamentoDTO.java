package br.gov.prf.silver.service.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SolicitacaoCancelamentoDTO {

	private RecolhimentoDTO recolhimento;
    private Long id;
    private String cpfSolicitante;
    private String nomeSolicitante;
    private String descricaoSolicitacao;
    private String descricaoMotivo;
    private LocalDateTime dhSolicitacao;
    private UsuarioLogadoDTO usuarioLogado;
    
}
