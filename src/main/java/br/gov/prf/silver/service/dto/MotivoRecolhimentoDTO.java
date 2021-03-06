package br.gov.prf.silver.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
public class MotivoRecolhimentoDTO {

    private Long id;
    private MotivoDTO motivo;
    private String outroMotivo;
    private String amparoLegal;
    private Long ano;
    private LocalDate dataPublicacao;
    private String tipoDocumento;
    private Boolean ativo;
    private LocalDateTime dhInclusao;
    private String cpfResponsavel;
    //usado no relatorio DRV
    private Boolean principal;
    
}
