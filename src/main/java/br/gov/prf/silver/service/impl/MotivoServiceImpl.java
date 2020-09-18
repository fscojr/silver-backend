package br.gov.prf.silver.service.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.Motivo;
import br.gov.prf.silver.repository.MotivoRepository;
import br.gov.prf.silver.service.MotivoService;
import br.gov.prf.silver.service.dto.MotivoDTO;
import br.gov.prf.silver.service.mapper.MotivoMapper;


/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class MotivoServiceImpl implements MotivoService {

	@Autowired private MotivoRepository motivoRepository;
	@Autowired private MotivoMapper motivoMapper;
	
	
	@Override
	public List<MotivoDTO> listaMotivos() {
		List<Motivo> motivos = this.motivoRepository.findAll();
		return this.motivoMapper.toDto(motivos);
	}

	@Override
	public MotivoDTO consultarMotivoPeloId(Long id) {
		Optional<Motivo> optional = motivoRepository.findById(id);
		MotivoDTO motivoDTO = optional
				.map(motivo -> motivoMapper.toDto(motivo))
				.orElseThrow(EntityNotFoundException::new);
        return motivoDTO;
	}
    
}
