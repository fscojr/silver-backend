package br.gov.prf.silver.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.Patio;
import br.gov.prf.silver.domain.TaPatioUnidade;
import br.gov.prf.silver.repository.TaPatioUnidadeRepository;
import br.gov.prf.silver.service.TaPatioUnidadeService;
import br.gov.prf.silver.service.dto.TaPatioUnidadeDTO;
import br.gov.prf.silver.service.mapper.TaPatioUnidadeMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class TaPatioUnidadeServiceImpl implements TaPatioUnidadeService {

	@Autowired
    private TaPatioUnidadeRepository taPatioUnidadeRepository;

	@Autowired
    private TaPatioUnidadeMapper taPatioUnidadeMapper;

	@Override
    public List<TaPatioUnidadeDTO> salvarPatioUnidade(List<TaPatioUnidadeDTO> patioUnidadeList, Patio patio) {
        List<TaPatioUnidade> patioUnidadeCollection = new ArrayList<>();
        for (TaPatioUnidadeDTO dto: patioUnidadeList) {
        	TaPatioUnidade unidadeDTO = taPatioUnidadeMapper.toEntity(dto);
        	unidadeDTO.setPatio(patio);
            this.taPatioUnidadeRepository.save(unidadeDTO);
            patioUnidadeCollection.add(unidadeDTO);
            
        }
        return taPatioUnidadeMapper.toDto(patioUnidadeCollection);
    }

	@Override
	public List<TaPatioUnidadeDTO> consultarPatioUnidadePorIdDoPatio(Long idPatio) {
		List<TaPatioUnidade> taPatioUnidade = taPatioUnidadeRepository.findAllByPatioId(idPatio);
        return taPatioUnidadeMapper.toDto(taPatioUnidade);
	}
	
	@Override
	public void deletarPatioUnidade(@Valid Long id) {
		taPatioUnidadeRepository.deleteById(id);
	}
	
}
