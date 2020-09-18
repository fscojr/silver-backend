package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.ContratoPatio;
import br.gov.prf.silver.domain.contratoPatio.ServicoContrato;
import br.gov.prf.silver.repository.DescricaoServicoRepository;
import br.gov.prf.silver.repository.ServicoContratoRepository;
import br.gov.prf.silver.service.ServicoContratoService;
import br.gov.prf.silver.service.dto.DescricaoServicoDTO;
import br.gov.prf.silver.service.dto.ServicoContratoDTO;
import br.gov.prf.silver.service.mapper.DescricaoServicoMapper;
import br.gov.prf.silver.service.mapper.ServicoContratoMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class ServicoContratoServiceImpl implements ServicoContratoService {

	@Autowired
    private ServicoContratoRepository servicoContratoRepository;

	@Autowired
    private ServicoContratoMapper servicoContratoMapper;
	
	@Autowired
    private DescricaoServicoRepository descricaoServicoContratoRepository;

	@Autowired
	private DescricaoServicoMapper descricaoServicoMapper;

	
	@Override
    public List<ServicoContratoDTO> salvarServicoContrato(List<ServicoContratoDTO> servicoContratoList, ContratoPatio contratoPatio) {
		
        List<ServicoContrato> servicoContratoCollection = new ArrayList<>();
        
        for (ServicoContratoDTO dto: servicoContratoList) {
        	ServicoContrato servicoContrato = servicoContratoMapper.toEntity(dto);
        	dataAcao(servicoContrato);
        	servicoContrato.setContratoPatio(contratoPatio);
            this.servicoContratoRepository.save(servicoContrato);
            servicoContratoCollection.add(servicoContrato);
        }
        return servicoContratoMapper.toDto(servicoContratoCollection);
    }
	
	@Override
	public List<ServicoContratoDTO> consultarServicoPorIdDoContrato(Long idContrato) {
		List<ServicoContrato> servicoContrato = servicoContratoRepository.findAllByContratoPatioId(idContrato);
        return servicoContratoMapper.toDto(servicoContrato);
	}

	@Override
    public List<DescricaoServicoDTO> recuperarListaDescricaoServico() {
        return descricaoServicoMapper.toDto(this.descricaoServicoContratoRepository.findAll());
    }
    
	private void dataAcao(ServicoContrato servicoContrato) {
		if(servicoContrato.getId() == null) {
			servicoContrato.setAtivo(true);
			servicoContrato.setDhInclusao(LocalDateTime.now());
			servicoContrato.setDhAtualizacao(LocalDateTime.now());
		} else {
			servicoContrato.setDhAtualizacao(LocalDateTime.now());
		}
	}
	
	@Override
	public void deletarServicoContrato(@Valid Long id) {
		servicoContratoRepository.deleteById(id);
	}

}
