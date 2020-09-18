package br.gov.prf.silver.service;

import java.util.List;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.gov.prf.silver.service.dto.SituacaoDTO;
import br.gov.prf.silver.service.dto.SituacaoSolicitacaoDTO;
import br.gov.prf.silver.service.dto.SolicitacaoCancelamentoDTO;
import br.gov.prf.silver.service.filtro.MonitorSolicitacaoFiltro;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface SolicitacaoCancelamentoService {
	
	SituacaoSolicitacaoDTO salvar(SolicitacaoCancelamentoDTO dto) throws MessagingException;
	Page<SituacaoSolicitacaoDTO> consultar(MonitorSolicitacaoFiltro filtro, Pageable pageable);
	SolicitacaoCancelamentoDTO consultarPorId(Long id);
	void deletar(@Valid Long id);
	
	SituacaoSolicitacaoDTO consultarSituacaoPorId(Long id);
	SituacaoSolicitacaoDTO salvarSituacao(SituacaoSolicitacaoDTO dto) throws MessagingException;
	List<SituacaoDTO> listaSituacoes();

	
}
