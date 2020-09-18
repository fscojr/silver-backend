package br.gov.prf.silver.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.repository.CategoriaContratualRepository;
import br.gov.prf.silver.service.CategoriaContratualService;
import br.gov.prf.silver.service.dto.CategoriaContratualDTO;
import br.gov.prf.silver.service.mapper.CategoriaContratualMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class CategoriaContratualServiceImpl implements CategoriaContratualService {

	@Autowired
    private CategoriaContratualRepository categoriaContratualRepository;
	
	@Autowired
	private CategoriaContratualMapper categoriaContratualMapper;

	
    public List<CategoriaContratualDTO> carregarListaCategoriaContratual() {
        return categoriaContratualMapper.toDto(this.categoriaContratualRepository.findAll());
    }

}
