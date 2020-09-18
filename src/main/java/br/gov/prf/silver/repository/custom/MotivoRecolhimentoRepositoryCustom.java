package br.gov.prf.silver.repository.custom;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.gov.prf.silver.domain.MotivoRecolhimento;
import br.gov.prf.silver.service.filtro.MotivoRecolhimentoFiltro;

/**
 * Create by bruno.abreu.prestador on January/2020
 */

@SuppressWarnings("unused")
@Repository
public interface MotivoRecolhimentoRepositoryCustom {

    List<MotivoRecolhimento> obterListaMotivoRecolhimentoPorParametro(MotivoRecolhimentoFiltro filtro);

}

