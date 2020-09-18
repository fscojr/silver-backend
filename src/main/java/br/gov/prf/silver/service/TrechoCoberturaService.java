package br.gov.prf.silver.service;

import java.util.List;

import javax.validation.Valid;

import br.gov.dprf.wsclient.servo.dominio.UfBr;
import br.gov.prf.silver.domain.ContratoPatio;
import br.gov.prf.silver.service.dto.TrechoCoberturaDTO;
import br.gov.prf.silver.service.dto.TrechoDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface TrechoCoberturaService {

    List<TrechoCoberturaDTO> salvarTrechoCobertura(List<TrechoCoberturaDTO> trechoCobertura, ContratoPatio contratoPatio);

    List<TrechoDTO> consultar(String siglaUf, String nomeBr, Long km);

    List<TrechoCoberturaDTO> consultarTrechoPorIdDoContrato(Long id);

    void deletarTrechoCobertura(@Valid Long id);

    List<UfBr> consultaBrPorUf(String uf);

}
