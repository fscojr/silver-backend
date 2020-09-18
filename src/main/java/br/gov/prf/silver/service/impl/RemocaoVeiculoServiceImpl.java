package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.recolhimento.RemocaoVeiculo;
import br.gov.prf.silver.domain.recolhimento.TipoRemocao;
import br.gov.prf.silver.repository.RemocaoVeiculoRepository;
import br.gov.prf.silver.repository.TipoRemocaoRepository;
import br.gov.prf.silver.service.RemocaoVeiculoService;
import br.gov.prf.silver.service.dto.RemocaoVeiculoDTO;
import br.gov.prf.silver.service.dto.TipoRemocaoDTO;
import br.gov.prf.silver.service.mapper.RemocaoVeiculoMapper;
import br.gov.prf.silver.service.mapper.TipoRemocaoMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class RemocaoVeiculoServiceImpl implements RemocaoVeiculoService {

    @Autowired
    private TipoRemocaoRepository tipoRemocaoRepository;

    @Autowired
    private TipoRemocaoMapper tipoRemocaoMapper;

    @Autowired
    private RemocaoVeiculoRepository remocaoVeiculoRepository;
    
    @Autowired
    private RemocaoVeiculoMapper remocaoVeiculoMapper;


    @Override
    public List<TipoRemocaoDTO> recuperarListaTiposRemocao() {
        return tipoRemocaoMapper.toDto(tipoRemocaoRepository.findAll());
    }

    @Override
    public RemocaoVeiculoDTO salvarRemocao(RemocaoVeiculoDTO dto) {
    	if(dto.getTipoRemocao() != null) {
	        RemocaoVeiculo remocaoVeiculo = this.remocaoVeiculoMapper.toEntity(dto);
	        recuperarTipoRemocaoPersistente(dto, remocaoVeiculo);
	        remocaoVeiculo.setDhInclusao(LocalDateTime.now());
	    	this.remocaoVeiculoRepository.save(remocaoVeiculo);
	        dto.setId(remocaoVeiculo.getId());
	        return dto;
    	}
    	return null;
        
    }

	private void recuperarTipoRemocaoPersistente(@Valid RemocaoVeiculoDTO dto, RemocaoVeiculo remocaoVeiculo) {
		Optional<TipoRemocao> optional = this.tipoRemocaoRepository.findById(dto.getTipoRemocao().getId());
		optional.ifPresent(remocaoVeiculo::setTipoRemocao);
	}

}
