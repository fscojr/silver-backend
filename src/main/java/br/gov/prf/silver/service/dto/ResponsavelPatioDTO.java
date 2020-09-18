package br.gov.prf.silver.service.dto;

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
public class ResponsavelPatioDTO {

    private Long id;

    private PatioDTO patio;
    
    private String nome;

    private String email;
    
    private LocalDateTime dhInclusao;
    
}
