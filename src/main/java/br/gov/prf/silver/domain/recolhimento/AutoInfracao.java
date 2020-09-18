package br.gov.prf.silver.domain.recolhimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutoInfracao {

    private String brInfracao;
    private String codigoInfracao;
    private String codigoUnidade;
    private Long dataHoraDigitacao;
    private Long dataHoraInfracao;
    private String descAbreviadaInfracao;
    private String descEspecie;
    private String descInfracao;
    private String docArrendatario;
    private String docCondutor;
    private String docInfrator;
    private String docProprietario;
    private String enquadramentoInfracao;
    private String excVerificado;
    private String gravidadeInfracao;
    private Long kmInfracao;
    private String matriculaAgente;
    private String nomInfrator;
    private String nomProprietario;
    private String nomeCondutor;
    private String numCnh;
    private String numeroAuto;
    private String placa;
    private String pontosInfracao;
    private String renavam;
    private String sentidoVia;
    private String statusAI;
    private String tipoAbordagem;
    private String tipoInfrator;
    private String ufCnh;
    private String ufInfracao;
    private String ufPlaca;
    private Float valorInfracao;
    private Boolean veiculoEstrangeiro;
    private VeiculoAutoInfracao veiculo;

}
