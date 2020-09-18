package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.recolhimento.EstadoVeiculo;
import br.gov.prf.silver.domain.recolhimento.NivelCombustivel;
import br.gov.prf.silver.repository.EstadoVeiculoRepository;
import br.gov.prf.silver.repository.NivelCombustivelRepository;
import br.gov.prf.silver.service.EstadoVeiculoService;
import br.gov.prf.silver.service.dto.EstadoVeiculoDTO;
import br.gov.prf.silver.service.dto.NivelCombustivelDTO;
import br.gov.prf.silver.service.mapper.EstadoVeiculoMapper;
import br.gov.prf.silver.service.mapper.NivelCombustivelMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class EstadoVeiculoServiceImpl implements EstadoVeiculoService {
    
    @Autowired private EstadoVeiculoMapper estadoVeiculoMapper;
    @Autowired private EstadoVeiculoRepository estadoVeiculoRepository;
    @Autowired private NivelCombustivelMapper nivelCombustivelMapper;
    @Autowired private NivelCombustivelRepository nivelCombustivelRepository;

    
    @Override
    public EstadoVeiculoDTO salvar(@Valid EstadoVeiculoDTO dto) {
    	EstadoVeiculo estadoVeiculo = this.estadoVeiculoMapper.toEntity(dto);
    	if(estadoVeiculo.getSemMarcadorCombustivel().equals(false)) {
    		recuperarNivelCombustivelPersistente(dto, estadoVeiculo);
    	}
    	estadoVeiculo.setDhInclusao(LocalDateTime.now());
    	this.estadoVeiculoRepository.save(estadoVeiculo);
        dto.setId(estadoVeiculo.getId());
        return dto;
    }
    
    @Override
	public EstadoVeiculoDTO consultarPorId(Long id) {
		Optional<EstadoVeiculo> estadoVeiculo = estadoVeiculoRepository.findById(id);
		EstadoVeiculoDTO estadoVeiculoDTO = estadoVeiculo
				.map(estadoOptional -> estadoVeiculoMapper.toDto(estadoOptional))
				.orElseThrow(EntityNotFoundException::new);
        return estadoVeiculoDTO;
	}

	@Override
    public List<NivelCombustivelDTO> recuperarListaNiveisCombustivel() {
        return nivelCombustivelMapper.toDto(nivelCombustivelRepository.findAll());
    }

	private void recuperarNivelCombustivelPersistente(EstadoVeiculoDTO dto, EstadoVeiculo estadoVeiculo) {
		Optional<NivelCombustivel> optional = this.nivelCombustivelRepository
				.findById(dto.getNivelCombustivel().getId());
		optional.ifPresent(estadoVeiculo::setNivelCombustivel);
	}
    
}
