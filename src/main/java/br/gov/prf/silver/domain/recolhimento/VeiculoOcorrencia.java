package br.gov.prf.silver.domain.recolhimento;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VeiculoOcorrencia {
    private String descricao;
    private String nivelAlerta;
    private VeiculoOcorrenciaSistema sistema;
}
