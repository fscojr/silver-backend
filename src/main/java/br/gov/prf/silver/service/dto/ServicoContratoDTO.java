package br.gov.prf.silver.service.dto;

import java.math.BigDecimal;
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
public class ServicoContratoDTO {

	private Long id;

	private ContratoPatioDTO contratoPatio;

    private TipoServicoContratoDTO tipoServico;

    private CategoriaContratualDTO categoriaContratual;

    private DescricaoServicoDTO descricaoServico;

    private String outraDescricao;

    private String unidade;

    private BigDecimal valor;

    private Boolean ativo;

    private LocalDateTime dhInclusao;

    private LocalDateTime dhAtualizacao;
    
}
