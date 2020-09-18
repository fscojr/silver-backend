package br.gov.prf.silver.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.prf.silver.service.dto.DadosRelatorioPesquisaDTO;
import br.gov.prf.silver.service.dto.MotivoDTO;
import br.gov.prf.silver.service.dto.MotivoRecolhimentoDTO;
import br.gov.prf.silver.service.filtro.MotivoRecolhimentoFiltro;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface MotivoRecolhimentoService {

	MotivoRecolhimentoDTO salvar(@Valid MotivoRecolhimentoDTO dto);
	
	Page<MotivoRecolhimentoDTO> consultar(MotivoRecolhimentoFiltro filtro, Pageable pageable);
	
	MotivoRecolhimentoDTO consultarPeloId(Long id);
	
	List<MotivoRecolhimentoDTO> listarAmparoIdMotivo(Long id);

	void deletar(Long id);
	
	List<MotivoDTO> listaMotivos();
	
	List<MotivoRecolhimentoDTO> consultarMotivoId(Long id);
	
	MotivoRecolhimentoDTO consultaAmparo(String amparo);
	
    List<DadosRelatorioPesquisaDTO> recuperaDadosRelatorio(MotivoRecolhimentoFiltro filtro);

}
