package br.gov.prf.silver.service.filtro;

import br.gov.prf.silver.domain.Motivo;
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
public class MotivoRecolhimentoFiltro {

    private Long motivo;
    private String outroMotivo;
    private String amparoLegal;
    private String tipoDocumento;
    private Boolean ativo;
    private Motivo motivoDTO;
    
}
