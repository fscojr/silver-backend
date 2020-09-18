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
public class VeiculoDTO {
	
	private Long id;
    private TipoVeiculoDTO tipoVeiculo;
    private EstadoVeiculoDTO estadoVeiculo;
    private EmplacamentoVeiculoDTO emplacamento;
    private String placa;
    private String chassi;
    private String renavam;
    private CategoriaVeiculoDTO categoriaVeiculo;
    private MarcaVeiculoDTO marcaVeiculo;
    private String outraMarca;
    private String modelo;
    private CorVeiculoDTO corVeiculo;
    private EspecieVeiculoDTO especieVeiculo;
    private String ufSigla;
    private PaisDTO pais;
    private String nomeProprietario;
    private String cpfCnpjProprietario;
    private Boolean ativo;
    private LocalDateTime dhInclusao;

}
