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
public class CondutorVeiculoDTO {

    private Long id;
    private TipoCondutorDTO tipo;
    private String nome;
    private String cpf;
    private Boolean proprietario;
    private TipoDocumentoCondutorDTO tipoDocumento;
    private String numeroDocumento;
    private String cnh;
    private Character categoriaCnh;
    private LocalDate validadeCnh;
    private PaisDTO pais;
    private String ufSigla;
    private Boolean situacao;
    private LocalDateTime dhInclusao;
    private String emailCondutor;
    private String telefoneCondutor;
    
}
