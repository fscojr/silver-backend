package br.gov.prf.silver.service;

import java.util.List;

import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.service.dto.PolicialRecolhimentoDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface PolicialRecolhimentoService {

    List<PolicialRecolhimentoDTO> salvarPolicialAuxilizar(List<PolicialRecolhimentoDTO> dto, Recolhimento recolhimento);
    PolicialRecolhimentoDTO salvarPolicialResponsavel(Recolhimento recolhimento);
    List<PolicialRecolhimentoDTO> consultarPorRecolhimentoId(Long id);
    List<PolicialRecolhimentoDTO> consultarPolicialPelaMatriculaCpf(String cpfMatricula);
}
