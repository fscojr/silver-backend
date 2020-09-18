package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.ContratoPatio;
import br.gov.prf.silver.domain.Patio;
import br.gov.prf.silver.domain.contratoPatio.TipoServicoContrato;
import br.gov.prf.silver.repository.ContratoPatioRepository;
import br.gov.prf.silver.repository.PatioRepository;
import br.gov.prf.silver.repository.TipoServicoContratoRepository;
import br.gov.prf.silver.repository.custom.ContratoPatioRepositoryCustom;
import br.gov.prf.silver.repository.specification.ContratoPatioSpecification;
import br.gov.prf.silver.security.exceptions.BusinessException;
import br.gov.prf.silver.service.ContratadaService;
import br.gov.prf.silver.service.ContratoPatioService;
import br.gov.prf.silver.service.ServicoContratoService;
import br.gov.prf.silver.service.TrechoCoberturaService;
import br.gov.prf.silver.service.UnidadeService;
import br.gov.prf.silver.service.dto.ContratoPatioDTO;
import br.gov.prf.silver.service.dto.DadosRelatorioPesquisaDTO;
import br.gov.prf.silver.service.dto.ServicoContratoDTO;
import br.gov.prf.silver.service.dto.TipoServicoContratoDTO;
import br.gov.prf.silver.service.dto.TrechoCoberturaDTO;
import br.gov.prf.silver.service.dto.UnidadeDTO;
import br.gov.prf.silver.service.filtro.ContratoPatioFiltro;
import br.gov.prf.silver.service.mapper.ContratoPatioMapper;
import br.gov.prf.silver.service.mapper.TipoServicoContratoMapper;


/**
 * Review by bruno.abreu.abreu on November, 2019
 */

@Service
@Transactional
public class ContratoPatioServiceImpl implements ContratoPatioService {

	@Autowired private ContratoPatioRepository contratoPatioRepository;
	@Autowired private ContratoPatioRepositoryCustom contratoPatioRepositoryCustom;
	@Autowired private TipoServicoContratoRepository tipoServicoContratoRepository;
	@Autowired private PatioRepository patioRepository;
	@Autowired private ContratoPatioMapper contratoPatioMapper;
	@Autowired private TipoServicoContratoMapper tipoServicoContratoMapper;
	@Autowired private ContratadaService contratadaService;
	@Autowired private ServicoContratoService servicoContratoService;
	@Autowired private TrechoCoberturaService trechoCoberturaService;
	@Autowired private UnidadeService unidadeService;
	@Autowired private MessageSource messageSource;
	
	
	@Override
	public ContratoPatioDTO salvarContratoPatio(@Valid ContratoPatioDTO dto) {
		
		contratadaService.salvarContratada(dto.getContratada());
		ContratoPatioDTO contratoPatioDTO = ajustarDtoSalvar(dto);
    	ContratoPatio contratoPatio = this.contratoPatioMapper.toEntity(contratoPatioDTO);
    	recuperarPatioPersistente(dto, contratoPatio);
    	dataAcao(contratoPatioDTO, contratoPatio);
    	this.contratoPatioRepository.save(contratoPatio);
        dto.setId(contratoPatio.getId());
        
        trechoCoberturaService.salvarTrechoCobertura(dto.getTrechoCobertura(), contratoPatio);
        servicoContratoService.salvarServicoContrato(dto.getServicoContrato(), contratoPatio);
        
        contratoPatioDTO.setId(contratoPatio.getId());
        return contratoPatioDTO;
	}

	private void dataAcao(ContratoPatioDTO contratoPatioDTO, ContratoPatio contratoPatio) {
		if(contratoPatioDTO.getId() == null) {
    		contratoPatio.setAtivo(true);
    		contratoPatio.setDhInclusao(LocalDateTime.now());
    		contratoPatio.setDhAtualizacao(LocalDateTime.now());
    	} else {
    		contratoPatio.setDhAtualizacao(LocalDateTime.now());
    	}
	}

	@Override
	public Page<ContratoPatioDTO> consultarContratoPatio(ContratoPatioFiltro filtro, Pageable pageable) {
		Specification<ContratoPatio> especificacao = Specification
				.where(ContratoPatioSpecification.filtroPesquisa(filtro));
        return contratoPatioRepository.findAll(especificacao, pageable).map(this::construirRetorno);
	}
	
	@Override
	public ContratoPatioDTO consultarContratoPatioPeloId(Long id) {
		Optional<ContratoPatio> optional = contratoPatioRepository.findById(id);
		ContratoPatioDTO contratoPatioDTO = optional
				.map(contratoPatio -> contratoPatioMapper.toDto(contratoPatio))
				.orElseThrow(EntityNotFoundException::new);
		List<TrechoCoberturaDTO> trechoCoberturaDTO = consultarListaPatioUnidade(id);
		List<ServicoContratoDTO> servicoContratoDTO = consultarServicoContrato(id);
		contratoPatioDTO = AjustarDtoEdicao(contratoPatioDTO, trechoCoberturaDTO, servicoContratoDTO);
        return contratoPatioDTO;
	}
	
	@Override
	public void deletarContratoPatio(@Valid Long id) {
		verificaServicoTrecho(id);
		contratoPatioRepository.deleteById(id);
	}

	private void verificaServicoTrecho(Long id) {
		List<ServicoContratoDTO> servico = servicoContratoService.consultarServicoPorIdDoContrato(id);
		List<TrechoCoberturaDTO> trecho = trechoCoberturaService.consultarTrechoPorIdDoContrato(id);

		if(!servico.isEmpty() || !trecho.isEmpty()) {
			throw new BusinessException(messageSource.getMessage("MSG001", null, LocaleContextHolder.getLocale()));
		}
	}

	@Override
	public List<TipoServicoContratoDTO> listarTipoServicoContrato() {
		List<TipoServicoContrato> tipoServicoContrato = this.tipoServicoContratoRepository.findAll();
		return this.tipoServicoContratoMapper.toDto(tipoServicoContrato);
	}
	
	@Override
	public List<DadosRelatorioPesquisaDTO> recuperaDadosRelatorio(ContratoPatioFiltro filtro){
		List<ContratoPatio> dadosRelatorio = contratoPatioRepositoryCustom
				.obterListaContratoPatioPorParametro(filtro);
        verificaResultadoDaBusca(dadosRelatorio);
        List<ContratoPatioDTO> dadosRelatorioCollection = ajustaRegionalUnidadeRelatorio(dadosRelatorio);
        List<DadosRelatorioPesquisaDTO> dadosCollection = ajustaDadosRelatorio(dadosRelatorioCollection);
        return dadosCollection;
	}

	private List<DadosRelatorioPesquisaDTO> ajustaDadosRelatorio(
			List<ContratoPatioDTO> dadosRelatorioCollection) {
		DadosRelatorioPesquisaDTO dadosDto = new DadosRelatorioPesquisaDTO();
        dadosDto.setDadosRelatorioList(dadosRelatorioCollection);
        List<DadosRelatorioPesquisaDTO> dadosCollection = new ArrayList<>();
        dadosCollection.add(dadosDto);
		return dadosCollection;
	}

	private List<ContratoPatioDTO> ajustaRegionalUnidadeRelatorio(List<ContratoPatio> dadosRelatorio) {
		List<ContratoPatioDTO> dadosRelatorioDTO = contratoPatioMapper.toDto(dadosRelatorio);
		List<ContratoPatioDTO> dadosRelatorioCollection = new ArrayList<>();
        for(ContratoPatioDTO dto : dadosRelatorioDTO) {
        	ContratoPatioDTO contratoDTO = new ContratoPatioDTO();
        	contratoDTO.setProcessoSei(dto.getProcessoSei());
        	contratoDTO.setNumeroContrato(dto.getNumeroContrato());
        	contratoDTO.setPatio(dto.getPatio());
        	contratoDTO.setDtInicioVigencia(dto.getDtInicioVigencia());
        	contratoDTO.setDtFimVigencia(dto.getDtFimVigencia());
        	contratoDTO.setDhAtualizacao(dto.getDhAtualizacao());
        	setRegionalDtoBusca(contratoDTO, dto);
        	setUnidadeSigla(contratoDTO, dto);
    		dadosRelatorioCollection.add(contratoDTO);
        }
		return dadosRelatorioCollection;
	}
	
	
	private void verificaResultadoDaBusca(List<ContratoPatio> dadosMultaRelatorio) {
		if (dadosMultaRelatorio.isEmpty()) {
            throw new BusinessException(messageSource.getMessage("MSG007", null, LocaleContextHolder.getLocale()));
        }
	}
	
	private ContratoPatioDTO ajustarDtoSalvar(ContratoPatioDTO dto) {
		ContratoPatioDTO contratoPatioDTO = new ContratoPatioDTO();
		if(dto.getId() != null) {
			contratoPatioDTO.setId(dto.getId());
		}
		contratoPatioDTO.setContratada(dto.getContratada());
		contratoPatioDTO.setPatio(dto.getPatio());
		contratoPatioDTO.setProcessoSei(dto.getProcessoSei());
		contratoPatioDTO.setNumeroContrato(dto.getNumeroContrato());
		contratoPatioDTO.setDtInicioVigencia(dto.getDtInicioVigencia());
		contratoPatioDTO.setDtFimVigencia(dto.getDtFimVigencia());
		contratoPatioDTO.setAtivo(dto.getAtivo());
		contratoPatioDTO.setDhInclusao(dto.getDhInclusao());
		contratoPatioDTO.setDhAtualizacao(dto.getDhAtualizacao());
		contratoPatioDTO.setRegional(dto.getRegional());
		contratoPatioDTO.setUnidade(dto.getUnidade());
		return contratoPatioDTO;
	}
	
	private void recuperarPatioPersistente(@Valid ContratoPatioDTO dto, ContratoPatio contratoPatio) {
		Optional<Patio> optional = this.patioRepository.findById(dto.getPatio().getId());
		optional.ifPresent(contratoPatio::setPatio);
	}
	
	protected ContratoPatioDTO construirRetorno(ContratoPatio contratoPatio) {
		ContratoPatioDTO dto = contratoPatioMapper.toDto(contratoPatio);
		dto = AjustarDtoBusca(dto);
    	return dto;
    }
	
	private List<TrechoCoberturaDTO> consultarListaPatioUnidade(Long id) {
		return trechoCoberturaService.consultarTrechoPorIdDoContrato(id);
	}

	private List<ServicoContratoDTO> consultarServicoContrato(Long id) {
		return servicoContratoService.consultarServicoPorIdDoContrato(id);
	}

	private ContratoPatioDTO AjustarDtoEdicao(ContratoPatioDTO dto, List<TrechoCoberturaDTO> trechoCoberturaDTO,
			List<ServicoContratoDTO> servicoContratoDTO) {
		ContratoPatioDTO contratoPatioDTO = new ContratoPatioDTO();
		contratoPatioDTO.setId(dto.getId());
		contratoPatioDTO.setPatio(dto.getPatio());
		contratoPatioDTO.setContratada(dto.getContratada());
		contratoPatioDTO.setProcessoSei(dto.getProcessoSei());
		contratoPatioDTO.setNumeroContrato(dto.getNumeroContrato());
		contratoPatioDTO.setDtInicioVigencia(dto.getDtInicioVigencia());
		contratoPatioDTO.setDtFimVigencia(dto.getDtFimVigencia());
		contratoPatioDTO.setAtivo(dto.getAtivo());
		contratoPatioDTO.setDhInclusao(dto.getDhInclusao());
		contratoPatioDTO.setDhAtualizacao(dto.getDhAtualizacao());
		setTrechoDtoEdicao(trechoCoberturaDTO, contratoPatioDTO);
		setServicoDtoEdicao(servicoContratoDTO, contratoPatioDTO);
		contratoPatioDTO.setRegional(dto.getRegional());
		contratoPatioDTO.setUnidade(dto.getUnidade());
		return contratoPatioDTO;
	}
    
    private void setTrechoDtoEdicao(List<TrechoCoberturaDTO> trechoCoberturaDTO, ContratoPatioDTO dto) {
		List<TrechoCoberturaDTO> trechoCoberturaList = new ArrayList<>();
		for(TrechoCoberturaDTO trechoCobertura : trechoCoberturaDTO) {
			TrechoCoberturaDTO trecho = new TrechoCoberturaDTO();
			trecho.setId(trechoCobertura.getId());
			trecho.setRodovia(trechoCobertura.getRodovia());
			trecho.setKmInicial(trechoCobertura.getKmInicial());
			trecho.setKmFinal(trechoCobertura.getKmFinal());
			trecho.setAtivo(trechoCobertura.getAtivo());
			trecho.setDhInclusao(trechoCobertura.getDhInclusao());
			trecho.setDhAtualizacao(trechoCobertura.getDhAtualizacao());
			trechoCoberturaList.add(trecho);
		}
		dto.setTrechoCobertura(trechoCoberturaList);
	}
    
    private void setServicoDtoEdicao(List<ServicoContratoDTO> servicoContratoDTO, ContratoPatioDTO dto) {
		List<ServicoContratoDTO> servicoList = new ArrayList<>();
		for(ServicoContratoDTO servicoContrato : servicoContratoDTO) {
			ServicoContratoDTO servico = new ServicoContratoDTO();
			servico.setId(servicoContrato.getId());
			servico.setTipoServico(servicoContrato.getTipoServico());
			servico.setCategoriaContratual(servicoContrato.getCategoriaContratual());
			servico.setDescricaoServico(servicoContrato.getDescricaoServico());
			servico.setOutraDescricao(servicoContrato.getOutraDescricao());
			servico.setUnidade(servicoContrato.getUnidade());
			servico.setValor(servicoContrato.getValor());
			servico.setAtivo(servicoContrato.getAtivo());
			servico.setDhInclusao(servicoContrato.getDhInclusao());
			servico.setDhAtualizacao(servicoContrato.getDhAtualizacao());
			servicoList.add(servico);
		}
		dto.setServicoContrato(servicoList);
	}
    
    private ContratoPatioDTO AjustarDtoBusca(ContratoPatioDTO dto) {
    	ContratoPatioDTO contratoDTO = new ContratoPatioDTO();
    	contratoDTO.setId(dto.getId());
    	contratoDTO.setProcessoSei(dto.getProcessoSei());
    	contratoDTO.setNumeroContrato(dto.getNumeroContrato());
    	contratoDTO.setPatio(dto.getPatio());
    	contratoDTO.setDtInicioVigencia(dto.getDtInicioVigencia());
    	contratoDTO.setDtFimVigencia(dto.getDtFimVigencia());
    	setRegionalDtoBusca(contratoDTO, dto);
		setUnidadeDtoBusca(contratoDTO, dto);
		return contratoDTO;
	}
    
    private void setRegionalDtoBusca(ContratoPatioDTO contratoDTO, ContratoPatioDTO dto) {
		UnidadeDTO unidade = unidadeService.buscarUnidade(dto.getRegional());	
		contratoDTO.setRegionalNome(unidade.getSigla());
	}
    
    private void setUnidadeDtoBusca(ContratoPatioDTO contratoDTO, ContratoPatioDTO dto) {
		UnidadeDTO unidade = unidadeService.buscarUnidade(dto.getUnidade());	
		contratoDTO.setUnidadeNome(unidade.getNome());
	}

    private void setUnidadeSigla(ContratoPatioDTO contratoDTO, ContratoPatioDTO dto) {
		UnidadeDTO unidade = unidadeService.buscarUnidade(dto.getUnidade());	
		contratoDTO.setUnidadeNome(unidade.getSigla());
	}
    
}
