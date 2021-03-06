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
public class OcorrenciaVeiculoDTO {

   private String descricao;
   
   private String nivelAlerta;

   private SistemaDTO sistema;
}
