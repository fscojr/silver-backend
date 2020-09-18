package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.Veiculo;
import br.gov.prf.silver.domain.recolhimento.Combinacao;
import br.gov.prf.silver.domain.recolhimento.Pertence;
import br.gov.prf.silver.domain.recolhimento.TipoPertence;
import br.gov.prf.silver.repository.CombinacaoRepository;
import br.gov.prf.silver.repository.PertenceRepository;
import br.gov.prf.silver.repository.TipoPertenceRepository;
import br.gov.prf.silver.repository.VeiculoRepository;
import br.gov.prf.silver.service.PertenceService;
import br.gov.prf.silver.service.dto.CombinacaoDTO;
import br.gov.prf.silver.service.dto.PertenceDTO;
import br.gov.prf.silver.service.dto.VeiculoDTO;
import br.gov.prf.silver.service.mapper.PertenceMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class PertenceServiceImpl implements PertenceService {

    @Autowired private PertenceRepository pertenceRepository;
    @Autowired private PertenceMapper pertenceMapper;
    @Autowired private VeiculoRepository veiculoRepository;
    @Autowired private CombinacaoRepository combinacaoRepository;
    @Autowired private TipoPertenceRepository tipoPertenceRepository;
    

    @Override
    public List<PertenceDTO> salvar(List<PertenceDTO> pertenceList, VeiculoDTO veiculo,
    		CombinacaoDTO combinacao) {
    	List<Pertence> pertenceCollection = new ArrayList<>();
        for (PertenceDTO dto: pertenceList) {
        	Pertence pertence = this.pertenceMapper.toEntity(dto);
        	if(veiculo != null) {
        		recuperarVeiculoPersistente(veiculo, pertence);
        	}
        	if(combinacao != null) {
        		recuperarCombinacaoPersistente(combinacao, pertence);
        	}
        	recuperarTipoPertencePersistente(dto, pertence);
        	pertence.setDhInclusao(LocalDateTime.now());
            this.pertenceRepository.save(pertence);
            pertenceCollection.add(pertence);
        }
        return pertenceMapper.toDto(pertenceCollection);
    }
    
    @Override
	public List<PertenceDTO> consultaVeiculoId(Long veiculoId) {
    	Optional<Veiculo> veiculo = veiculoRepository.findById(veiculoId);
    	if(veiculo.isPresent()) {
    		List<Pertence> pertenceList = pertenceRepository.findAllByVeiculo(veiculo);
    		return pertenceMapper.toDto(pertenceList);
    	}
    	return null;
	}    
    
    
    private void recuperarVeiculoPersistente(@Valid VeiculoDTO dto, Pertence pertence) {
		Optional<Veiculo> optional = this.veiculoRepository.findById(dto.getId());
		optional.ifPresent(pertence::setVeiculo);
	}
    
    private void recuperarCombinacaoPersistente(@Valid CombinacaoDTO dto, Pertence pertence) {
		Optional<Combinacao> optional = this.combinacaoRepository.findById(dto.getId());
		optional.ifPresent(pertence::setCombinacao);
	}
    
    private void recuperarTipoPertencePersistente(@Valid PertenceDTO dto, Pertence pertence) {
		Optional<TipoPertence> optional = this.tipoPertenceRepository.findById(dto.getTipoPertence().getId());
		optional.ifPresent(pertence::setTipoPertence);
	}

}
