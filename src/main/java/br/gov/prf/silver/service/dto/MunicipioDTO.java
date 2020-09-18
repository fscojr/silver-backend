package br.gov.prf.silver.service.dto;

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
public class MunicipioDTO {

    private Long id;
    private String nome;
    private String ibgeAntigo;
    private UfDTO uf;

}
