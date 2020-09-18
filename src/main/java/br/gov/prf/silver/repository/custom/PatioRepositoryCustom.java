package br.gov.prf.silver.repository.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.Patio;
import br.gov.prf.silver.service.filtro.PatioFiltro;

/**
 * Create by bruno.abreu.prestador on January/2020
 */

@SuppressWarnings("unused")
@Repository
public interface PatioRepositoryCustom {

    List<Patio> obterListaPatioPorParametro(PatioFiltro filtro);

}

