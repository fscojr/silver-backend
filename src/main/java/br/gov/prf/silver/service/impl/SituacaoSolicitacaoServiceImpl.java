package br.gov.prf.silver.service.impl;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.domain.recolhimento.SituacaoSolicitacao;
import br.gov.prf.silver.repository.RecolhimentoRepository;
import br.gov.prf.silver.repository.SituacaoSolicitacaoRepository;
import br.gov.prf.silver.service.SituacaoSolicitacaoService;
import br.gov.prf.silver.service.dto.SituacaoSolicitacaoDTO;
import br.gov.prf.silver.service.mapper.SituacaoSolicitacaoMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class SituacaoSolicitacaoServiceImpl implements SituacaoSolicitacaoService {
    
    @Autowired private SituacaoSolicitacaoMapper situacaoMapper;
    @Autowired private SituacaoSolicitacaoRepository situacaoRepository;
    @Autowired private RecolhimentoRepository recolhimentoRepository;
    
    
    @Override
    public SituacaoSolicitacaoDTO salvar(@Valid SituacaoSolicitacaoDTO dto) {
    	SituacaoSolicitacao situacao = this.situacaoMapper.toEntity(dto);
    	recuperarRecolhimentoPersistente(dto, situacao);
		this.situacaoRepository.save(situacao);
        return this.situacaoMapper.toDto(situacao);
    }
    
    private void recuperarRecolhimentoPersistente(SituacaoSolicitacaoDTO dto, 
    		SituacaoSolicitacao situacao) {
		Optional<Recolhimento> optional = this.recolhimentoRepository.findById(dto.getRecolhimento().getId());
		optional.ifPresent(situacao::setRecolhimento);
	}
    
	@Override
	public SituacaoSolicitacaoDTO consultarPorId(Long id) {
		Optional<SituacaoSolicitacao> situacao = situacaoRepository.findById(id);
		SituacaoSolicitacaoDTO situacaoDTO = situacao.map(optional -> situacaoMapper.toDto(optional))
				.orElseThrow(EntityNotFoundException::new);
        return situacaoDTO;
	}
	
}
