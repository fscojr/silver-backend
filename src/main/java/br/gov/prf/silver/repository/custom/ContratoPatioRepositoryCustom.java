package br.gov.prf.silver.repository.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.ContratoPatio;
import br.gov.prf.silver.service.filtro.ContratoPatioFiltro;

/**
 * Create by bruno.abreu.prestador on January/2020
 */

@SuppressWarnings("unused")
@Repository
public interface ContratoPatioRepositoryCustom {

    List<ContratoPatio> obterListaContratoPatioPorParametro(ContratoPatioFiltro filtro);

}

