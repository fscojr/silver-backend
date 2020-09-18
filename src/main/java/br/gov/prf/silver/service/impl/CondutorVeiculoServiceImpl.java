package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.Pais;
import br.gov.prf.silver.domain.recolhimento.CondutorVeiculo;
import br.gov.prf.silver.domain.recolhimento.TipoCondutor;
import br.gov.prf.silver.domain.recolhimento.TipoDocumentoCondutor;
import br.gov.prf.silver.repository.CondutorVeiculoRepository;
import br.gov.prf.silver.repository.PaisRepository;
import br.gov.prf.silver.repository.TipoCondutorRepository;
import br.gov.prf.silver.repository.TipoDocumentoCondutorRepository;
import br.gov.prf.silver.service.CondutorVeiculoService;
import br.gov.prf.silver.service.dto.CondutorVeiculoDTO;
import br.gov.prf.silver.service.dto.TipoCondutorDTO;
import br.gov.prf.silver.service.dto.TipoDocumentoCondutorDTO;
import br.gov.prf.silver.service.mapper.CondutorVeiculoMapper;
import br.gov.prf.silver.service.mapper.TipoCondutorMapper;
import br.gov.prf.silver.service.mapper.TipoDocumentoCondutorMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class CondutorVeiculoServiceImpl implements CondutorVeiculoService {

    @Autowired private TipoDocumentoCondutorRepository tipoDocumentoCondutorRepository;
    @Autowired private TipoCondutorRepository tipoCondutorRepository;
    @Autowired private CondutorVeiculoRepository condutorVeiculoRepository;
    @Autowired private PaisRepository paisRepository;
    @Autowired private CondutorVeiculoMapper condutorVeiculoMapper;
	@Autowired private TipoCondutorMapper tipoCondutorMapper;
	@Autowired private TipoDocumentoCondutorMapper tipoDocumentoCondutorMapper;

    
    @Override
    public CondutorVeiculoDTO salvarCondutor(CondutorVeiculoDTO dto) {
		dto.setDhInclusao(LocalDateTime.now());
        CondutorVeiculo condutorVeiculo = this.condutorVeiculoMapper.toEntity(dto);
        recuperarTipoCondutorPersistente(dto, condutorVeiculo);
        recuperarTipoDocumentoCondutorPersistente(dto, condutorVeiculo);
        recuperarPaisPersistente(dto, condutorVeiculo);
        condutorVeiculo.setAtivo(true);
        this.condutorVeiculoRepository.save(condutorVeiculo);
        dto.setId(condutorVeiculo.getId());
        return dto;
    }
    
    @Override
    public List<TipoCondutorDTO> recuperarListaTiposCondutor() {
        return tipoCondutorMapper.toDto(tipoCondutorRepository.findAll());
    }

    @Override
    public TipoCondutorDTO consultarTipoCondutorPorId(Long id) {
    	Optional<TipoCondutor> optional = tipoCondutorRepository.findById(id);
    	TipoCondutorDTO tipoCondutorDTO = optional
				.map(tipoCondutor -> tipoCondutorMapper.toDto(tipoCondutor))
				.orElseThrow(EntityNotFoundException::new);
        return tipoCondutorDTO;
    }
    
    @Override
    public List<TipoDocumentoCondutorDTO> recuperarListaTiposDocCondutor() {
        return tipoDocumentoCondutorMapper.toDto(tipoDocumentoCondutorRepository.findAll());
    }
    
    
    private void recuperarTipoCondutorPersistente(@Valid CondutorVeiculoDTO dto, CondutorVeiculo condutorVeiculo) {
		Optional<TipoCondutor> optional = this.tipoCondutorRepository.findById(dto.getTipo().getId());
		optional.ifPresent(condutorVeiculo::setTipo);
	}
    
    private void recuperarTipoDocumentoCondutorPersistente(@Valid CondutorVeiculoDTO dto, CondutorVeiculo condutorVeiculo) {
		Optional<TipoDocumentoCondutor> optional = this.tipoDocumentoCondutorRepository.findById(dto.getTipoDocumento().getId());
		optional.ifPresent(condutorVeiculo::setTipoDocumento);
	}
    
    private void recuperarPaisPersistente(@Valid CondutorVeiculoDTO dto, CondutorVeiculo condutorVeiculo) {
    	if(dto.getPais().getId() != null) {
	    	Optional<Pais> optional = this.paisRepository.findById(dto.getPais().getId());
	    	optional.ifPresent(condutorVeiculo::setPais);
    	}
	}

}
