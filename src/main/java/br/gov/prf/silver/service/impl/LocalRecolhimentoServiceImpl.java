package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.recolhimento.LocalRecolhimento;
import br.gov.prf.silver.enums.SentidoEnum;
import br.gov.prf.silver.repository.LocalRecolhimentoRepository;
import br.gov.prf.silver.service.LocalRecolhimentoService;
import br.gov.prf.silver.service.dto.LocalRecolhimentoDTO;
import br.gov.prf.silver.service.mapper.LocalRecolhimentoMapper;


/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class LocalRecolhimentoServiceImpl implements LocalRecolhimentoService {

	@Autowired private LocalRecolhimentoMapper localRecolhimentoMapper;
	@Autowired private LocalRecolhimentoRepository localRecolhimentoRepository;


    @Override
    public LocalRecolhimentoDTO salvarLocalRecolhimento(LocalRecolhimentoDTO dto) {
        LocalRecolhimento localRecolhimento = localRecolhimentoMapper.toEntity(dto);
        dataAcao(localRecolhimento);
        localRecolhimento.setSentido(getCodigoSentido(dto));
    	localRecolhimentoRepository.save(localRecolhimento);
        dto.setId(localRecolhimento.getId());
        return dto;
    }


	private void dataAcao(LocalRecolhimento localRecolhimento) {
		if(localRecolhimento.getId() == null) {
			localRecolhimento.setDhInclusao(LocalDateTime.now());
		}
	}

	private Long getCodigoSentido(LocalRecolhimentoDTO dto) {
		return dto.getSentidoSg() == SentidoEnum.CRESCENTE.getDescricao() ? 
				SentidoEnum.CRESCENTE.getCodigo() : SentidoEnum.DECRESCENTE.getCodigo();
	}

}
