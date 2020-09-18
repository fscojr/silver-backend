package br.gov.prf.silver.service.dto;

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
public class AutoInfracaoVeiculoDTO {

	//TODO: revisar
    private int anoFabricacao;

    private int anoModelo;

    private int anoUltimoLicenciamento;

    private String cambio;

    private float capacidadeCarga;

    private float capacidadeMaximaDeTracao;

    private int capacidadePassageiros;

    private String categoria;

    private int cilindradas;

    private int codigoCombustivel;

    private String proprietarioEmpresa;

    private int codigoCor;

    private int codigoEspecie;

    private int especie;

    private int codigoMarcaModelo;

    private int codigoMunicipioEmplacamento;

    private int codigoRestricao1;

    private int codigoRestricao2;

    private int codigoRestricao3;

    private int codigoRestricao4;

    private int codigoTipo;

    private String procedenciaVeiculo;

    private String codigoTipoCarroceria;

    private String combustivel;

    private String cor;

    private long dataEmissaoCrv;

    private String descricaoTipoCarroceria;

    private String descricaoTipoDocumentoProprietario;

    private String marcaModelo;

    private String marca;

    private String motor;

    private String municipioEmplacamento;

    private int numeroEixos;

    private String numeroIdFaturamento;

    private String ufEmplacamento;

    private String ufFaturamento;

    private String situacaoVeiculo;

    private int potenciaVeiculo;

    private String pais;

    private float pesoBrutoTotal;

    private String tipo;

    private List<OcorrenciaVeiculoDTO> ocorrencias;
    
}
