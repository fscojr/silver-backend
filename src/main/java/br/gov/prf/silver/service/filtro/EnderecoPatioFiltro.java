package br.gov.prf.silver.service.filtro;

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
public class EnderecoPatioFiltro {

    private Long id;
    private String endereco;
    private String bairro;
    private String cep;
    private Long municipio;
    private String referencia;
    private String telefone;

}
