package br.gov.prf.silver.repository.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.service.filtro.RecolhimentoFiltro;

/**
 * Create by bruno.abreu.prestador on January/2020
 */

@SuppressWarnings("unused")
@Repository
public interface RecolhimentoRepositoryCustom {

    List<Recolhimento> obterListaRecolhimentoPorParametro(RecolhimentoFiltro filtro);

}

