package br.gov.prf.silver.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.repository.TipoPertenceRepository;
import br.gov.prf.silver.service.TipoPertenceService;
import br.gov.prf.silver.service.dto.TipoPertenceDTO;
import br.gov.prf.silver.service.mapper.TipoPertenceMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */

@Service
@Transactional
public class TipoPertenceServiceImpl implements TipoPertenceService {

    @Autowired
    private TipoPertenceRepository tipoPertenceRepository;
    
    @Autowired
    private TipoPertenceMapper tipoPertenceMapper;


    @Override
    public List<TipoPertenceDTO> recuperarListaTipoPertence() {
        return tipoPertenceMapper.toDto(this.tipoPertenceRepository.findAll());
    }

}
