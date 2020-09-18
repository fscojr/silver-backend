package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import br.gov.prf.silver.domain.ContratoPatio;
import br.gov.prf.silver.domain.Patio;
import br.gov.prf.silver.domain.PatioHst;
import br.gov.prf.silver.domain.ResponsavelPatio;
import br.gov.prf.silver.domain.TaPatioUnidade;
import br.gov.prf.silver.domain.TipoPatio;
import br.gov.prf.silver.repository.ContratoPatioRepository;
import br.gov.prf.silver.repository.PatioHstRepository;
import br.gov.prf.silver.repository.PatioRepository;
import br.gov.prf.silver.repository.ResponsavelPatioRepository;
import br.gov.prf.silver.repository.TaPatioUnidadeRepository;
import br.gov.prf.silver.repository.TipoPatioRepository;
import br.gov.prf.silver.repository.specification.PatioSpecification;
import br.gov.prf.silver.security.exceptions.BusinessException;
import br.gov.prf.silver.service.EnderecoService;
import br.gov.prf.silver.service.PatioService;
import br.gov.prf.silver.service.ResponsavelPatioService;
import br.gov.prf.silver.service.TaPatioUnidadeService;
import br.gov.prf.silver.service.UnidadeService;
import br.gov.prf.silver.service.dto.EnderecoPatioDTO;
import br.gov.prf.silver.service.dto.PatioDTO;
import br.gov.prf.silver.service.dto.PatioHstDTO;
import br.gov.prf.silver.service.dto.ResponsavelPatioDTO;
import br.gov.prf.silver.service.dto.TaPatioUnidadeDTO;
import br.gov.prf.silver.service.dto.UnidadeDTO;
import br.gov.prf.silver.service.dto.dominio.TipoPatioDTO;
import br.gov.prf.silver.service.dto.resumido.ResponsavelPatioNome;
import br.gov.prf.silver.service.dto.resumido.UnidadeSigla;
import br.gov.prf.silver.service.filtro.PatioFiltro;
import br.gov.prf.silver.service.mapper.PatioHstMapper;
import br.gov.prf.silver.service.mapper.PatioMapper;
import br.gov.prf.silver.service.mapper.TipoPatioMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class PatioServiceImpl implements PatioService {
    
    @Autowired private PatioMapper patioMapper;
    @Autowired private PatioHstMapper patioHstMapper;
    @Autowired private TipoPatioMapper tipoPatioMapper;
    @Autowired private PatioRepository patioRepository;
    @Autowired private PatioHstRepository patioHstRepository;
    @Autowired private TipoPatioRepository tipoPatioRepository;
    @Autowired private TaPatioUnidadeService taPatioUnidadeService;
    @Autowired private TaPatioUnidadeRepository taPatioUnidadeRepository;
    @Autowired private ContratoPatioRepository  contratoPatioRepositoy;
    @Autowired private ResponsavelPatioService responsavelPatioService;
    @Autowired private ResponsavelPatioRepository responsavelPatioRepository;
    @Autowired private EnderecoService enderecoService;
    @Autowired private UnidadeService unidadeService;
	@Autowired private MessageSource messageSource;

    
    @Override
    public PatioDTO salvarPatio(@Valid PatioDTO dto) {
    	PatioDTO dtoPatio = ajustaPatioDtoParaPersistir(dto);
    	salvarEnderecoPatio(dtoPatio);
    	Patio patio = this.patioMapper.toEntity(dtoPatio);
    	recuperarTipoPatioPersistente(dto, patio);
    	dataHoraAcao(dtoPatio, patio);
    	verificaDuplicidadeNomePatio(dto);
		this.patioRepository.save(patio);
    	salvarPatioUnidade(dto, patio);
    	salvarResponsavelPatio(dto, patio);
        dto.setId(patio.getId());
        salvarHistorico(dto);
        return dto;
    }

	private void salvarHistorico(PatioDTO dto) {
		PatioHstDTO dtoHst = new PatioHstDTO();
		BeanUtils.copyProperties(dto, dtoHst);
        PatioHst hst = this.patioHstMapper.toEntity(dto);
		recuperarPatioPersistente(dto.getId(), hst);
		hst.setDhInclusao(LocalDateTime.now());
		hst.setCpfResponsavel("00000000000");//TODO: ajustar para o cpf de quem esta logado
		this.patioHstRepository.save(hst);
	}

	@Override
	public Page<PatioDTO> consultarPatio(PatioFiltro filtro, Pageable pageable) {
        Specification<Patio> especificacao = Specification.where(PatioSpecification.filtroPesquisa(filtro));
        return patioRepository.findAll(especificacao, pageable).map(this::construirRetorno);
    }
	
	@Override
	public PatioDTO consultarPatioPorId(Long id) {
		Optional<Patio> patio = patioRepository.findById(id);
		PatioDTO patioDTO = patio
				.map(optional -> patioMapper.toDto(optional))
				.orElseThrow(EntityNotFoundException::new);
		List<TaPatioUnidadeDTO> taPatioUnidadeDTO = buscarListaPatioUnidade(id);
		List<ResponsavelPatioDTO> responsavelPatioDTO = buscarResponsavelPatio(id);
		patioDTO = AjustarDtoEdicao(patioDTO, taPatioUnidadeDTO, responsavelPatioDTO);
        return patioDTO;
	}

	@Override
	public void deletarPatio(@Valid Long id) {
		verificaPermissaoParaExclusao(id);
		excluiPatioHst(id);
		excluiPatioUnidade(id);
		excluiResponsavelPatio(id);
		patioRepository.deleteById(id);
	}

	private void excluiPatioHst(Long id) {
		List<PatioHst> hst = patioHstRepository.findByPatioId(id);
		for(PatioHst patio : hst) {
			patioHstRepository.deleteById(patio.getId());
		}
	}

	@Override
	public List<TipoPatioDTO> listarTiposPatio() {
		List<TipoPatio> tiposPatios = tipoPatioRepository.findAll();
		List<TipoPatio> tiposPatiosAtivos = tiposPatios.stream().filter(f -> f.getSituacao().equals(true)).collect(Collectors.toList());
		return this.tipoPatioMapper.toDto(tiposPatiosAtivos);
	}

	@Override
	public List<PatioDTO> consultarPatioPorUnidade(Long id) {
		List<TaPatioUnidade> patioUnidadeList = taPatioUnidadeRepository.findAllByUnidade(id);
		
		List<Long> patioList = new ArrayList<>();
		for( TaPatioUnidade patioUnidade : patioUnidadeList) {
			patioList.add(patioUnidade.getPatio().getId());
		}
		
		List<Patio> patio = patioRepository.findAllById(patioList);
		return patioMapper.toDto(patio);
	}
	
	
	private void dataHoraAcao(PatioDTO dtoPatio, Patio patio) {
		if(dtoPatio.getId() == null) {
    		patio.setDhInclusao(LocalDateTime.now());
    	}else {
    		patio.setDhInclusao(patio.getDhInclusao());
    		patio.setDhAtualizacao(LocalDateTime.now());
    	}
	}
	
	private PatioDTO ajustaPatioDtoParaPersistir(PatioDTO dto) {
		PatioDTO dtoPatio = new PatioDTO();
    	dtoPatio.setId(dto.getId());
    	dtoPatio.setNome(dto.getNome());
    	dtoPatio.setEnderecoPatio(dto.getEnderecoPatio());
    	dtoPatio.setAtivo(dto.getAtivo());
    	dtoPatio.setMetragem(dto.getMetragem());
    	dtoPatio.setDhInclusao(dto.getDhInclusao());
    	dtoPatio.setDhAtualizacao(dto.getDhAtualizacao());
    	return dtoPatio;
	}
	
	private void verificaDuplicidadeNomePatio(PatioDTO dto) {
		List<Patio> patioList = this.patioRepository.findAllByNomeContainingIgnoreCase(dto.getNome());
    	//TODO: ajustar futuramente com stream.
    	for(Patio patioL : patioList) {
    		List<TaPatioUnidade> unidadeList = this.taPatioUnidadeRepository.findAllByPatioId(patioL.getId());
    		for(TaPatioUnidade unidade : unidadeList) {
    			for(TaPatioUnidadeDTO taUnidade : dto.getUnidade()) {
    				if(unidade.getUnidade().equals(taUnidade.getUnidade()) && dto.getId() == null) {
        				throw new BusinessException(messageSource.getMessage("MSG008", null, LocaleContextHolder.getLocale()));
        			}    				
    			}
    		}
    	}
	}

	private void salvarEnderecoPatio(PatioDTO dtoPatio) {
		EnderecoPatioDTO endereco = enderecoService.salvarEnderecoPatio(dtoPatio.getEnderecoPatio());
    	dtoPatio.setEnderecoPatio(endereco);
	}

    private void recuperarTipoPatioPersistente(@Valid PatioDTO dto, Patio patio) {
		Optional<TipoPatio> optional = this.tipoPatioRepository.findById(dto.getTipoPatio().getId());
		optional.ifPresent(patio::setTipoPatio);
	}
    
    private void salvarPatioUnidade(PatioDTO dto, Patio patio) {
    	taPatioUnidadeService.salvarPatioUnidade(dto.getUnidade(), patio);
    }
    
    private void salvarResponsavelPatio(PatioDTO dto, Patio patio) {
    	responsavelPatioService.salvarResponsavelPatio(dto.getResponsavel(), patio);
    }
    
    private PatioDTO AjustarDtoEdicao(PatioDTO dto, List<TaPatioUnidadeDTO> taPatioUnidadeDTO,
			List<ResponsavelPatioDTO> responsavelPatioDTO) {
		PatioDTO patioDTO = new PatioDTO();
		patioDTO.setId(dto.getId());
		patioDTO.setNome(dto.getNome());
		patioDTO.setTipoPatio(dto.getTipoPatio());
		patioDTO.setEnderecoPatio(dto.getEnderecoPatio());
		patioDTO.setAtivo(dto.getAtivo());
		patioDTO.setMetragem(dto.getMetragem());
		setUnidadeDtoEdicao(taPatioUnidadeDTO, patioDTO);
		setResponsavelPatioEdicao(responsavelPatioDTO, patioDTO);
		patioDTO.setDhInclusao(dto.getDhInclusao());
		patioDTO.setDhAtualizacao(dto.getDhAtualizacao());
		return patioDTO;
	}
    
    private void setUnidadeDtoEdicao(List<TaPatioUnidadeDTO> taPatioUnidadeDTO, PatioDTO dto) {
		List<UnidadeDTO> unidadeList = new ArrayList<>();
		for(TaPatioUnidadeDTO taPatioUnidade : taPatioUnidadeDTO) {
			UnidadeDTO unidade = unidadeService.buscarUnidade(taPatioUnidade.getUnidade());	
			UnidadeDTO unidadeResumida = new UnidadeDTO();
			unidadeResumida.setId(taPatioUnidade.getId());
			unidadeResumida.setIdUnidade(unidade.getId());
			unidadeResumida.setNome(unidade.getNome());
			unidadeResumida.setSigla(unidade.getSigla());
			unidadeResumida.setUnidadePai(taPatioUnidade.getRegional());
			unidadeList.add(unidadeResumida);
		}
		dto.setUnidades(unidadeList);
	}
    
    private void setResponsavelPatioEdicao(List<ResponsavelPatioDTO> responsavelPatioDTO, PatioDTO dto) {
		List<ResponsavelPatioDTO> responsavelList = new ArrayList<>();
		for(ResponsavelPatioDTO responsavelPatio : responsavelPatioDTO) {
			ResponsavelPatioDTO responsavel = new ResponsavelPatioDTO();
			responsavel.setId(responsavelPatio.getId());
			responsavel.setNome(responsavelPatio.getNome());
			responsavel.setEmail(responsavelPatio.getEmail());
			responsavel.setDhInclusao(responsavelPatio.getDhInclusao());
			responsavelList.add(responsavel);
		}
		dto.setResponsavel(responsavelList);
	}

	private List<ResponsavelPatioDTO> buscarResponsavelPatio(Long id) {
		return responsavelPatioService
				.consultarResponsavelPatioPeloIdDoPatio(id);
	}

	private List<TaPatioUnidadeDTO> buscarListaPatioUnidade(Long id) {
		return taPatioUnidadeService.consultarPatioUnidadePorIdDoPatio(id);
	}
	
    protected PatioDTO construirRetorno(Patio patio) {
    	PatioDTO dto = patioMapper.toDto(patio);
    	List<TaPatioUnidadeDTO> taPatioUnidadeDTO = buscarListaPatioUnidade(dto.getId());
		List<ResponsavelPatioDTO> responsavelPatioDTO = buscarResponsavelPatio(dto.getId());
		dto = AjustarDtoBusca(dto, taPatioUnidadeDTO, responsavelPatioDTO);
        return dto;
    }
    
    private PatioDTO AjustarDtoBusca(PatioDTO dto, List<TaPatioUnidadeDTO> taPatioUnidadeDTO,
			List<ResponsavelPatioDTO> responsavelPatioDTO) {
    	PatioDTO patioDTO = new PatioDTO();
    	patioDTO.setId(dto.getId());
    	patioDTO.setNome(dto.getNome());
		setTipoPatio(dto, patioDTO);
		setUnidadeDtoBusca(taPatioUnidadeDTO, patioDTO);
		setResponsavelPatioBusca(responsavelPatioDTO, patioDTO)	;
		return patioDTO;
	}

	private void setUnidadeDtoBusca(List<TaPatioUnidadeDTO> taPatioUnidadeDTO, PatioDTO dto) {
		List<UnidadeSigla> unidadeList = new ArrayList<>();
		List<UnidadeSigla> regionalList = new ArrayList<>();
		for(TaPatioUnidadeDTO taPatioUnidade : taPatioUnidadeDTO) {
			UnidadeDTO unidade = unidadeService.buscarUnidade(taPatioUnidade.getUnidade());	
			UnidadeSigla unidadeResumida = new UnidadeSigla();
			unidadeResumida.setSigla(unidade.getNome());
			unidadeList.add(unidadeResumida);
			
			//set regional, com filter
			UnidadeDTO regional = unidadeService.buscarUnidade(unidade.getUnidadePai());
			UnidadeSigla regionalResumida = new UnidadeSigla();
			regionalResumida.setSigla(regional.getSigla());
			regionalList.add(regionalResumida);
			
		}
		dto.setRegionalList(regionalList);
		dto.setUnidadeList(unidadeList);
	}
	
	private void setResponsavelPatioBusca(List<ResponsavelPatioDTO> responsavelPatioDTO, PatioDTO dto) {
		List<ResponsavelPatioNome> responsavelList = new ArrayList<>();
		for(ResponsavelPatioDTO responsavelPatio : responsavelPatioDTO) {
			ResponsavelPatioNome responsavel = new ResponsavelPatioNome();
			responsavel.setNome(responsavelPatio.getNome());
			responsavelList.add(responsavel);
		}
		dto.setResponsavelList(responsavelList);
	}
	
	private void setTipoPatio(PatioDTO patioDTO, PatioDTO dto) {
		TipoPatioDTO tipoPatio = new TipoPatioDTO();
		tipoPatio.setDescricao(patioDTO.getTipoPatio().getDescricao());
		dto.setTipoPatio(tipoPatio);
	}
	
	private void verificaPermissaoParaExclusao(Long id) {
		List<ContratoPatio> contratoPatio = contratoPatioRepositoy.findAllByPatioId(id);
		if(!contratoPatio.isEmpty()) {
			throw new BusinessException(messageSource.getMessage("MSG001", null, LocaleContextHolder.getLocale()));
		}
	}	
	
	private void excluiResponsavelPatio(Long id) {
		List<ResponsavelPatio> responsavelPatioList = responsavelPatioRepository.findAllByPatioId(id);
		for(ResponsavelPatio responsavelPatio : responsavelPatioList) {
			responsavelPatioRepository.deleteById(responsavelPatio.getId());
		}
	}

	private void excluiPatioUnidade(Long id) {
		List<TaPatioUnidade> patioUnidadeList = taPatioUnidadeRepository.findAllByPatioId(id);
		for(TaPatioUnidade patioUnidade : patioUnidadeList) {
			taPatioUnidadeRepository.deleteById(patioUnidade.getId());
		}
	}
	
	private void recuperarPatioPersistente(Long id, PatioHst hst) {
		Optional<Patio> optional = this.patioRepository.findById(id);
		optional.ifPresent(hst::setPatio);
	}
    
}
