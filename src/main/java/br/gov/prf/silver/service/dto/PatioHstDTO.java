package br.gov.prf.silver.service.dto;

import java.time.LocalDateTime;

import br.gov.prf.silver.domain.Patio;
import br.gov.prf.silver.service.dto.dominio.TipoPatioDTO;
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
public class PatioHstDTO {

    private Patio patio;
    private String nome;
    private TipoPatioDTO tipoPatio;
    private EnderecoPatioDTO enderecoPatio;
    private Boolean ativo;
    private Long metragem;
    private LocalDateTime dhInclusao;
    private String cpfResponsavel;
    
}
