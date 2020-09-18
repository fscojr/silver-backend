package br.gov.prf.silver.service.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.gov.prf.silver.service.dto.dominio.TipoPatioDTO;
import br.gov.prf.silver.service.dto.resumido.ResponsavelPatioNome;
import br.gov.prf.silver.service.dto.resumido.UnidadeSigla;
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
public class PatioDTO {

    private Long id;
    private String nome;
    private TipoPatioDTO tipoPatio;
    private EnderecoPatioDTO enderecoPatio;
    private Boolean ativo;
    private Long metragem;
    private LocalDateTime dhInclusao;
    private LocalDateTime dhAtualizacao;
    
    //Para buscar
    private List<UnidadeSigla> regionalList;
    private List<UnidadeSigla> unidadeList;
    private List<ResponsavelPatioNome> responsavelList;
    
    //Para Edição
    private List<UnidadeDTO> unidades;

    //Para persistencencia
    private List<TaPatioUnidadeDTO> unidade;
    private List<ResponsavelPatioDTO> responsavel;
    
}
