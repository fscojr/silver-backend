package br.gov.prf.silver.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfiguracaoEnvioEmailDTO {

    private Long id;
    private String user;
    private String password;
    private String hostname;
    private Integer porta;
    private String remetente;
    private Boolean indAutenticacao;
    private Boolean indTLS;   
    private Integer timeout;
    
}
