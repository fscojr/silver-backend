package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.recolhimento.Combinacao;
import br.gov.prf.silver.repository.CombinacaoRepository;
import br.gov.prf.silver.service.CombinacaoVeiculoService;
import br.gov.prf.silver.service.EstadoVeiculoService;
import br.gov.prf.silver.service.PertenceService;
import br.gov.prf.silver.service.dto.CombinacaoDTO;
import br.gov.prf.silver.service.dto.EstadoVeiculoDTO;
import br.gov.prf.silver.service.dto.PertenceDTO;
import br.gov.prf.silver.service.dto.VeiculoDTO;
import br.gov.prf.silver.service.mapper.CombinacaoMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class CombinacaoVeiculoServiceImpl implements CombinacaoVeiculoService {
    
    @Autowired private CombinacaoMapper combinacaoMapper;
    @Autowired private CombinacaoRepository combinacaoRepository;
    @Autowired private EstadoVeiculoService estadoVeiculoService;
    @Autowired private PertenceService pertenceService;
    
    
	@Override
    public List<CombinacaoDTO> salvarCombinacao(List<CombinacaoDTO> combinacaoList, VeiculoDTO veiculo) {
		if(combinacaoList != null && !combinacaoList.isEmpty()) {
			List<Combinacao> combinacoesCollection = new ArrayList<>();
	        for (CombinacaoDTO dto: combinacaoList) {
	        	dto.setVeiculo(veiculo);
	        	verificaEstadoVeiculo(dto);
        		Combinacao combinacao = combinacaoMapper.toEntity(dto);
	            combinacao.setDhInclusao(LocalDateTime.now());
	            this.combinacaoRepository.save(combinacao);
	        	verificaPertence(dto, combinacao);
	            combinacoesCollection.add(combinacao);
	        }
	        return combinacaoMapper.toDto(combinacoesCollection);
		}
		return null;
    }
	
	@Override
    public List<CombinacaoDTO> consultarPorVeiculoId(Long id) {
        List<Combinacao> combinacao = this.combinacaoRepository.findByVeiculoId(id);
        return combinacaoMapper.toDto(combinacao);
    }


	private void verificaPertence(CombinacaoDTO dto, Combinacao combinacao) {
		if(dto.getPertenceList() != null) {
			dto.setId(combinacao.getId());
			dto.setPertenceList(salvarPertence(dto));
		}
	}

	private void verificaEstadoVeiculo(CombinacaoDTO dto) {
		if(dto.getVeiculo().getEstadoVeiculo() != null) {
			dto.setEstadoVeiculo(salvarEstadoCombinacao(dto));
		}
	}
	
	private EstadoVeiculoDTO salvarEstadoCombinacao(CombinacaoDTO dto) {
		return estadoVeiculoService.salvar(dto.getVeiculo().getEstadoVeiculo());
	}
	
	private List<PertenceDTO> salvarPertence(CombinacaoDTO dto) {
		return pertenceService.salvar(dto.getPertenceList(), null, dto);
	}
	
}
