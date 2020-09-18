package br.gov.prf.silver.service.dto;

import java.time.LocalDateTime;

import br.gov.prf.silver.domain.EnderecoPatio;
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
public class EnderecoPatioHstDTO {

    private EnderecoPatio enderecoPatio;
    private String endereco;
    private String bairro;
    private String cep;
    private Long municipio;
    private String ufSigla;
    private String referencia;
    private String telefone;
    private LocalDateTime dhInclusao;
    private String cpfResponsavel;

}
