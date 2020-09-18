package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.Motivo;
import br.gov.prf.silver.domain.MotivoRecolhimento;
import br.gov.prf.silver.domain.MotivoRecolhimentoHst;
import br.gov.prf.silver.domain.recolhimento.MotivoRecolhimentoAuto;
import br.gov.prf.silver.repository.MotivoRecolhimentoAutoRepository;
import br.gov.prf.silver.repository.MotivoRecolhimentoHstRepository;
import br.gov.prf.silver.repository.MotivoRecolhimentoRepository;
import br.gov.prf.silver.repository.MotivoRepository;
import br.gov.prf.silver.repository.custom.MotivoRecolhimentoRepositoryCustom;
import br.gov.prf.silver.repository.specification.MotivoRecolhimentoSpecification;
import br.gov.prf.silver.security.exceptions.BusinessException;
import br.gov.prf.silver.service.MotivoRecolhimentoService;
import br.gov.prf.silver.service.dto.DadosRelatorioPesquisaDTO;
import br.gov.prf.silver.service.dto.MotivoDTO;
import br.gov.prf.silver.service.dto.MotivoRecolhimentoDTO;
import br.gov.prf.silver.service.dto.MotivoRecolhimentoHstDTO;
import br.gov.prf.silver.service.filtro.MotivoRecolhimentoFiltro;
import br.gov.prf.silver.service.mapper.MotivoMapper;
import br.gov.prf.silver.service.mapper.MotivoRecolhimentoHstMapper;
import br.gov.prf.silver.service.mapper.MotivoRecolhimentoMapper;


/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class MotivoRecolhimentoServiceImpl implements MotivoRecolhimentoService {

	@Autowired private MotivoRecolhimentoRepository motivoRecolhimentoRepository;
	@Autowired private MotivoRecolhimentoRepositoryCustom motivoRecolhimentoRepositoryCustom;
	@Autowired private MotivoRecolhimentoHstRepository motivoRecolhimentoHstRepository;
	@Autowired private MotivoRepository motivoRepository;
	@Autowired private MotivoRecolhimentoAutoRepository motivoRecolhimentoAutoRepository;
	@Autowired private MotivoRecolhimentoMapper motivoRecolhimentoMapper;
	@Autowired private MotivoRecolhimentoHstMapper motivoRecolhimentoHstMapper;
	@Autowired private MotivoMapper motivoMapper;
	@Autowired private MessageSource messageSource;
	
	
	@Override
	public MotivoRecolhimentoDTO salvar(MotivoRecolhimentoDTO dto) {
    	MotivoRecolhimento motivoRecolhimento = this.motivoRecolhimentoMapper.toEntity(dto);
    	if(dto.getId() == null) {
    		motivoRecolhimento.setAtivo(true);
			motivoRecolhimento.setDhInclusao(LocalDateTime.now());
		}
		verificaSeExisteMotivoAmparoLegalCadastrados(dto);
    	recuperarMotivoPersistente(dto, motivoRecolhimento);
    	this.motivoRecolhimentoRepository.save(motivoRecolhimento);
    	dto.setId(motivoRecolhimento.getId());
    	salvarHistorico(dto);
        return dto;
	}

	private void salvarHistorico(MotivoRecolhimentoDTO dto) {
		MotivoRecolhimentoHstDTO dtoHst = new MotivoRecolhimentoHstDTO();
		BeanUtils.copyProperties(dto, dtoHst);
		MotivoRecolhimentoHst hst = this.motivoRecolhimentoHstMapper.toEntity(dto);
		recuperarMotivoRecolhimentoPersistente(dto.getId(), hst);
		hst.setAtivo(true);
		hst.setDhInclusao(LocalDateTime.now());
		hst.setCpfResponsavel("00000000000");//TODO: ajustar para o cpf de quem esta logado
		this.motivoRecolhimentoHstRepository.save(hst);
	}

	@Override
	public Page<MotivoRecolhimentoDTO> consultar(MotivoRecolhimentoFiltro filtro, 
			Pageable pageable) {
        Specification<MotivoRecolhimento> especificacao = Specification.where(
        		MotivoRecolhimentoSpecification.filtroPesquisa(filtro));
        return motivoRecolhimentoRepository.findAll(especificacao, pageable).map(this::construirRetorno);
    }
	
	@Override
	public MotivoRecolhimentoDTO consultarPeloId(Long id) {
		Optional<MotivoRecolhimento> optional = motivoRecolhimentoRepository.findById(id);
		MotivoRecolhimentoDTO motivoRecolhimentoDTO = optional
				.map(motivoRecolhimento -> motivoRecolhimentoMapper.toDto(motivoRecolhimento))
				.orElseThrow(EntityNotFoundException::new);
        return motivoRecolhimentoDTO;
	}

	@Override
	public void deletar(Long id) {
		verificaMotivoRecolhimentoAuto(id);
		deletaHstMotivoRecolhimento(id);
        motivoRecolhimentoRepository.deleteById(id);
	}

	private void deletaHstMotivoRecolhimento(Long id) {
		List<MotivoRecolhimentoHst> hst = motivoRecolhimentoHstRepository
				.findByMotivoRecolhimentoId(id);
		for(MotivoRecolhimentoHst motivo : hst) {
			motivoRecolhimentoHstRepository.deleteById(motivo.getId());
		}
	}
	
	@Override
	public MotivoRecolhimentoDTO consultaAmparo(String amparo) {
		return motivoRecolhimentoMapper.toDto(motivoRecolhimentoRepository
				.findByAmparoLegalContainingIgnoreCase(amparo));
	}

	@Override
	public List<MotivoDTO> listaMotivos() {
		List<Motivo> motivos = this.motivoRepository.findAll();
		return this.motivoMapper.toDto(motivos);
	}
	
	@Override
	public List<MotivoRecolhimentoDTO> consultarMotivoId(Long motivoId) {
		List<MotivoRecolhimento> motivoRecolhimento = motivoRecolhimentoRepository
				.findAllByMotivoId(motivoId);
		verificaResultadoEncontrado(motivoRecolhimento);
        return motivoRecolhimentoMapper.toDto(motivoRecolhimento);
	}

	@Override
	public List<MotivoRecolhimentoDTO> listarAmparoIdMotivo(Long id) {
		List<MotivoRecolhimento> motivos = motivoRecolhimentoRepository.findAllByMotivoId(id);
		verificaResultadoEncontrado(motivos);
        return motivoRecolhimentoMapper.toDto(motivos);
	}

	private void verificaResultadoEncontrado(List<MotivoRecolhimento> motivos) {
		if(motivos.isEmpty()) {
			throw new BusinessException(messageSource.getMessage("MSG003", null, LocaleContextHolder.getLocale()));
		}
	}
	
	@Override
	public List<DadosRelatorioPesquisaDTO> recuperaDadosRelatorio(MotivoRecolhimentoFiltro filtro) {
		recuperaMotivoPorId(filtro);
		List<MotivoRecolhimento> dadosRelatorio = motivoRecolhimentoRepositoryCustom
				.obterListaMotivoRecolhimentoPorParametro(filtro);
		List<MotivoRecolhimentoDTO> dadosRelatorioDTO = motivoRecolhimentoMapper.toDto(dadosRelatorio);
		List<DadosRelatorioPesquisaDTO> dadosCollection = ajustaDadosRelatorio(dadosRelatorioDTO);
		return dadosCollection;
	}

	private void recuperaMotivoPorId(MotivoRecolhimentoFiltro filtro) {
		if(filtro.getMotivo() != null) {
			Optional<Motivo> optional = this.motivoRepository.findById(filtro.getMotivo());
			if(optional.isPresent()) {
				filtro.setMotivoDTO(optional.get());
			}
		}
	}
	
	private List<DadosRelatorioPesquisaDTO> ajustaDadosRelatorio(
			List<MotivoRecolhimentoDTO> dadosRelatorioDTO) {
		DadosRelatorioPesquisaDTO dadosDto = new DadosRelatorioPesquisaDTO();
        dadosDto.setDadosRelatorioList(dadosRelatorioDTO	);
        List<DadosRelatorioPesquisaDTO> dadosCollection = new ArrayList<>();
        dadosCollection.add(dadosDto);
		return dadosCollection;
	}
	
	
	private void verificaSeExisteMotivoAmparoLegalCadastrados(MotivoRecolhimentoDTO dto) {
		MotivoRecolhimento motivo = motivoRecolhimentoRepository
				.findByMotivoIdAndAmparoLegal(dto.getMotivo().getId(), dto.getAmparoLegal());
		if((motivo != null) && (motivo.getId() != dto.getId())) {
			throw new BusinessException(
					messageSource.getMessage("MSG006", null, LocaleContextHolder.getLocale()));
		}
	}
	
	protected MotivoRecolhimentoDTO construirRetorno(MotivoRecolhimento motivoRecolhimento) {
    	return motivoRecolhimentoMapper.toDto(motivoRecolhimento);
    }

	private void verificaMotivoRecolhimentoAuto(Long id) {
		List<MotivoRecolhimentoAuto> motivoRecolhimentoAuto = motivoRecolhimentoAutoRepository
				.findAllByMotivoRecolhimentoId(id);
		if(!motivoRecolhimentoAuto.isEmpty()) {
			throw new BusinessException(messageSource.getMessage("MSG001", null, LocaleContextHolder.getLocale()));
		}
	}
	
	private void recuperarMotivoPersistente(@Valid MotivoRecolhimentoDTO dto, MotivoRecolhimento motivoRecolhimento) {
		Optional<Motivo> optional = this.motivoRepository.findById(dto.getMotivo().getId());
		optional.ifPresent(motivoRecolhimento::setMotivo);
	}
	
	private void recuperarMotivoRecolhimentoPersistente(Long id, MotivoRecolhimentoHst hst) {
		Optional<MotivoRecolhimento> optional = this.motivoRecolhimentoRepository.findById(id);
		optional.ifPresent(hst::setMotivoRecolhimento);
	}
	
}
