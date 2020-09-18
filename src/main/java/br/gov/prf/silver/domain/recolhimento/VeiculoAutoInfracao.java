package br.gov.prf.silver.domain.recolhimento;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import br.gov.dprf.wsclient.bat.dominio.enums.TipoVeiculo;
import br.gov.prf.silver.service.dto.RecolhimentoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VeiculoAutoInfracao {

    private Long id;
    private Long ufId;
    private Long paisId;
    private String chassi;
    private String renavam;
    private String placa;
    private String outraMarca;
    private String modelo;
    private String nomeProprietario;
    private Boolean ativo;
    private String documentoProprietario;
    private TipoVeiculo tipoVeiculo;
    private EstadoVeiculo estadoVeiculo;
    private EmplacamentoVeiculo emplacamento;
    private CategoriaVeiculo categoriaVeiculo;
    private MarcaVeiculo marcaVeiculo;
    private CorVeiculo corVeiculo;
    private EspecieVeiculo especieVeiculo;
    private List<Pertence> pertences;
    private List<Combinacao> combinacoes;
    private Instant createdAt;
    private Long anoFabricacao;
    private Long anoModelo;
    private Long anoUltimoLicenciamento;
    private String cambio;
    private float capacidadeCarga;
    private float capacidadeMaximaDeTracao;
    private Long capacidadePassageiros;
    private String categoria;
    private Long cilindradas;
    private Long codigoCombustivel;
    private String proprietarioEmpresa;
    private Long codigoCor;
    private Long codigoEspecie;
    private Long especie;
    private Long codigoMarcaModelo;
    private Long codigoMunicipioEmplacamento;
    private Long codigoRestricao1;
    private Long codigoRestricao2;
    private Long codigoRestricao3;
    private Long codigoRestricao4;
    private Long codigoTipo;
    private String procedenciaVeiculo;
    private String codigoTipoCarroceria;
    private String combustivel;
    private String cor;
    private Long dataEmissaoCrv;
    private String descricaoTipoCarroceria;
    private String descricaoTipoDocumentoProprietario;
    private String marcaModelo;
    private String marca;
    private String motor;
    private String municipioEmplacamento;
    private Long numeroEixos;
    private String numeroIdFaturamento;
    private String ufEmplacamento;
    private String ufFaturamento;
    private String situacaoVeiculo;
    private Long potenciaVeiculo;
    private String pais;
    private float pesoBrutoTotal;
    private String tipo;
    private List<VeiculoOcorrencia> ocorrencias;
    private List<RecolhimentoDTO> recolhimento;

    public boolean isProprietarioEmpresa() {
        if (this.getDocumentoProprietario().length() > 11) {
            return true;
        }
        return false;
    }

}
