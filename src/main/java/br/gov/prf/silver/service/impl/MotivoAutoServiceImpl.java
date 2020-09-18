package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.MotivoRecolhimento;
import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.domain.recolhimento.MotivoRecolhimentoAuto;
import br.gov.prf.silver.repository.MotivoRecolhimentoAutoRepository;
import br.gov.prf.silver.repository.MotivoRecolhimentoRepository;
import br.gov.prf.silver.repository.RecolhimentoRepository;
import br.gov.prf.silver.security.exceptions.BusinessException;
import br.gov.prf.silver.service.MotivoAutoService;
import br.gov.prf.silver.service.MotivoRecolhimentoService;
import br.gov.prf.silver.service.dto.MotivoRecolhimentoAutoDTO;
import br.gov.prf.silver.service.dto.MotivoRecolhimentoDTO;
import br.gov.prf.silver.service.mapper.MotivoAutoMapper;
import br.gov.prf.silver.service.mapper.MotivoRecolhimentoMapper;


/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class MotivoAutoServiceImpl implements MotivoAutoService {

	@Autowired
	private MotivoRecolhimentoService motivoRecolhimentoService;

	@Autowired
	private MotivoRecolhimentoMapper motivoRecolhimentoMapper;

	@Autowired
	private MotivoRecolhimentoRepository motivoRecolhimentoRepository;
	
	@Autowired
	private MotivoRecolhimentoAutoRepository motivoAutoRepository;

	@Autowired
	private MotivoAutoMapper motivoAutoMapper;

	@Autowired
	private RecolhimentoRepository recolhimentoRepository;

	@Autowired
    private MessageSource messageSource;
	
	
	@Override
	public List<MotivoRecolhimentoAutoDTO> salvar(List<MotivoRecolhimentoAutoDTO> motivoAutoList, Recolhimento recolhimento) {
        MotivoRecolhimentoDTO motivoRecolhimento = null;
        List<MotivoRecolhimentoAuto> motiviAutoCollection = new ArrayList<>();
        
        for (MotivoRecolhimentoAutoDTO dto : motivoAutoList) {
            MotivoRecolhimentoAuto motivo = motivoAutoMapper.toEntity(dto);
            motivoRecolhimento = consultaMotivoRecolhimento(dto);
            motivo.setMotivoRecolhimento(motivoRecolhimentoMapper.toEntity(motivoRecolhimento));
            motivo.setAutoDeInfracao(dto.getAutoDeInfracao());
            motivo.setDhInclusao(LocalDateTime.now());
            motivo.setPrincipal(veificaMotivoPrincipal(dto));
            recuperarRecolhimentoPersistente(recolhimento, motivo);
            validaMotivoRecolhimentoAutoInfracaoExiste(motivo);
            this.motivoAutoRepository.save(motivo);
            motiviAutoCollection.add(motivo);
        }
        
        return motivoAutoMapper.toDto(motiviAutoCollection);
	}

	private boolean veificaMotivoPrincipal(MotivoRecolhimentoAutoDTO dto) {
		return dto.getPrincipal() != null ? dto.getPrincipal() : false;
	}
	
	@Override
	public List<MotivoRecolhimentoDTO> buscarPorRecolhimentoId(Long recolhimentoId) {
    	Optional<Recolhimento> recolhimento = recolhimentoRepository.findById(recolhimentoId);
    	if(recolhimento.isPresent()) {
    		List<MotivoRecolhimentoAuto> motivoAutoList = motivoAutoRepository.findAllByRecolhimento(recolhimento);
    		return buscaMotivoRecolhimento(motivoAutoList);
    	}
		return null;
	}

	private List<MotivoRecolhimentoDTO> buscaMotivoRecolhimento(List<MotivoRecolhimentoAuto> motivoAutoList) {
		List<MotivoRecolhimentoDTO> motivoRecolhimentoCollection = new ArrayList<>();
		for (MotivoRecolhimentoAuto entity: motivoAutoList) {
			MotivoRecolhimentoDTO motivoRecolhimentoDTO = new MotivoRecolhimentoDTO();
			Optional<MotivoRecolhimento> optional = motivoRecolhimentoRepository
					.findById(entity.getMotivoRecolhimento().getId());
			if (optional.isPresent()) {
				MotivoRecolhimento motivoRecolhimento = optional.get();
				motivoRecolhimentoDTO = motivoRecolhimentoMapper.toDto(motivoRecolhimento);
				motivoRecolhimentoDTO.setPrincipal(entity.getPrincipal());
				motivoRecolhimentoCollection.add(motivoRecolhimentoDTO);
			}
		}
		return motivoRecolhimentoCollection;
	}
	
	
	private void validaMotivoRecolhimentoAutoInfracaoExiste(MotivoRecolhimentoAuto motivo) {
		MotivoRecolhimentoAuto motivoAuto = motivoAutoRepository
				.findByAutoDeInfracaoAndMotivoRecolhimentoId(motivo.getAutoDeInfracao(), motivo.getMotivoRecolhimento().getId());
		if(motivoAuto != null) {
			throw new BusinessException(messageSource.getMessage("MSG004", null, LocaleContextHolder.getLocale()));
		}
	}
	
	private void recuperarRecolhimentoPersistente(@Valid Recolhimento recolhimento, MotivoRecolhimentoAuto motivo) {
		Optional<Recolhimento> optional = this.recolhimentoRepository.findById(recolhimento.getId());
		optional.ifPresent(motivo::setRecolhimento);
	}

	private MotivoRecolhimentoDTO consultaMotivoRecolhimento(MotivoRecolhimentoAutoDTO dto) {
		MotivoRecolhimentoDTO motivoRecolhimento;
		if (dto.getMotivoRecolhimento().getId() != null) {
		    motivoRecolhimento = motivoRecolhimentoService
		    		.consultarPeloId(dto.getMotivoRecolhimento().getId());
		} else {
		    motivoRecolhimento = motivoRecolhimentoService
		    		.consultaAmparo(dto.getMotivoRecolhimento().getAmparoLegal());
		}
		if (motivoRecolhimento == null) {
		    throw new BusinessException("O motivo de recolhimento '" + dto
		    		.getMotivoRecolhimento().getAmparoLegal()+"' não está cadastrado no SILVER");
		}
		return motivoRecolhimento;
	}

}
