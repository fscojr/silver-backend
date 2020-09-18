package br.gov.prf.silver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.prf.silver.domain.recolhimento.SituacaoRecolhimento;
import br.gov.prf.silver.service.dto.DadosRelatorioPesquisaDTO;
import br.gov.prf.silver.service.dto.RecolhimentoDTO;
import br.gov.prf.silver.service.dto.SituacaoDTO;
import br.gov.prf.silver.service.filtro.RecolhimentoFiltro;

/**
 * Review by bruno.abreu.prestador on April/2020
 */
public interface RecolhimentoService {

	RecolhimentoDTO salva(RecolhimentoDTO dto);
	
	void atualizaSituacao(Long recolhimentoId, Long situacao);
	
	Page<RecolhimentoDTO> consulta(RecolhimentoFiltro filtro, Pageable pageable);
	
	RecolhimentoDTO consultaPorId(Long id);
	
	List<RecolhimentoDTO> consultaVeiculoSemLiberacao(String placa);
	
	RecolhimentoDTO consultaPeloDrv(String drv);
	
	List<SituacaoDTO> consultaSituacaoRecolhimento();
	
    List<DadosRelatorioPesquisaDTO> recuperaDadosRelatorio(RecolhimentoFiltro filtro);
	
	Optional<SituacaoRecolhimento> recuperaSituacao(Long situacao);
}
