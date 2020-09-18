package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.gov.dprf.seguranca.dominio.UsuarioLogin;
import br.gov.prf.silver.domain.recolhimento.SituacaoCancelamento;
import br.gov.prf.silver.domain.recolhimento.SituacaoSolicitacao;
import br.gov.prf.silver.domain.recolhimento.SolicitacaoCancelamento;
import br.gov.prf.silver.enums.SituacaoCancelamentoEnum;
import br.gov.prf.silver.enums.SituacaoRecolhimentoEnum;
import br.gov.prf.silver.repository.SituacaoCancelamentoRepository;
import br.gov.prf.silver.repository.SituacaoSolicitacaoRepository;
import br.gov.prf.silver.repository.SolicitacaoCancelamentoRepository;
import br.gov.prf.silver.repository.specification.SolicitacaoSpecification;
import br.gov.prf.silver.service.EnviaEmailService;
import br.gov.prf.silver.service.RecolhimentoService;
import br.gov.prf.silver.service.SolicitacaoCancelamentoService;
import br.gov.prf.silver.service.UsuarioLogadoService;
import br.gov.prf.silver.service.dto.SituacaoDTO;
import br.gov.prf.silver.service.dto.SituacaoSolicitacaoDTO;
import br.gov.prf.silver.service.dto.SolicitacaoCancelamentoDTO;
import br.gov.prf.silver.service.filtro.MonitorSolicitacaoFiltro;
import br.gov.prf.silver.service.mapper.SituacaoCancelamentoMapper;
import br.gov.prf.silver.service.mapper.SituacaoSolicitacaoMapper;
import br.gov.prf.silver.service.mapper.SolicitacaoCancelamentoMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class SolicitacaoCancelamentoServiceImpl implements SolicitacaoCancelamentoService {
    
    @Autowired private SolicitacaoCancelamentoRepository solicitacaoRepository;
    @Autowired private SituacaoCancelamentoRepository situacaoCancelamentoRepository;
    @Autowired private SituacaoSolicitacaoRepository situacaoSolicitacaoRepository;
    @Autowired private SolicitacaoCancelamentoMapper solicitacaoMapper;
    @Autowired private SituacaoCancelamentoMapper situacaoCancelamentoMapper;
    @Autowired private SituacaoSolicitacaoMapper situacaoSolicitacaoMapper;
    @Autowired private UsuarioLogadoService usuarioLogadoService;
    @Autowired private RecolhimentoService recolhimentoService;    
    @Autowired private EnviaEmailService enviaEmailService;    

    
    @Override
    public SituacaoSolicitacaoDTO salvar(@Valid SolicitacaoCancelamentoDTO dto) throws MessagingException {
    	
    	SolicitacaoCancelamento solicitacao = this.solicitacaoMapper.toEntity(dto);
    			
    	if(dto.getDescricaoMotivo() != null && dto.getDescricaoMotivo() != "") {
    		dto.setDescricaoSolicitacao(null);
    		solicitacao.setDescricaoSolicitacao(null);
    	} else {
    		dto.setDescricaoMotivo(null);
    		solicitacao.setDescricaoMotivo(null);
    	}
    	solicitacao.setCpfSolicitante(recuperaUsuario().getCpf());
		solicitacao.setNomeSolicitante(recuperaUsuario().getNome());
		solicitacao.setDhSolicitacao(LocalDateTime.now());
		this.solicitacaoRepository.save(solicitacao);
		dto.setId(solicitacao.getId());
		
		return this.preSalvaSituacao(dto);
    }

	@Override
	public Page<SituacaoSolicitacaoDTO> consultar(MonitorSolicitacaoFiltro filtro, Pageable pageable) {
        Specification<SituacaoSolicitacao> especificacao = Specification
        		.where(SolicitacaoSpecification.filtroPesquisa(filtro));
        return this.situacaoSolicitacaoRepository.findAll(especificacao, pageable).map(this::construirRetorno);
    }
	
	@Override
	public SolicitacaoCancelamentoDTO consultarPorId(Long id) {
		Optional<SolicitacaoCancelamento> solicitacao = this.solicitacaoRepository.findById(id);
		SolicitacaoCancelamentoDTO solicitacaoDTO = solicitacao
				.map(optional -> this.solicitacaoMapper.toDto(optional))
				.orElseThrow(EntityNotFoundException::new);
        return solicitacaoDTO;
	}

	@Override
	public void deletar(@Valid Long id) {
		this.solicitacaoRepository.deleteById(id);
	}
	
	@Override
	public SituacaoSolicitacaoDTO consultarSituacaoPorId(Long id) {
		Optional<SituacaoSolicitacao> situacao = this.situacaoSolicitacaoRepository.findById(id);
		SituacaoSolicitacaoDTO dto = situacao
				.map(optional -> this.situacaoSolicitacaoMapper.toDto(optional))
				.orElseThrow(EntityNotFoundException::new);
        return dto;
	}
	
	@Override
    public SituacaoSolicitacaoDTO salvarSituacao(@Valid SituacaoSolicitacaoDTO dto) throws MessagingException {

		dto.setRecolhimento(dto.getRecolhimento());
		dto.setSolicitacao(dto.getSolicitacao());
		dto.setSituacao(buscaSituacaoCancelamento(dto.getSituacao().getId()));
		dto.setDhInclusao(LocalDateTime.now());
		dto.setUsuarioInclusao(recuperaUsuario().getMatricula());

		return this.salvarSituacaoSolicitacao(this.situacaoSolicitacaoMapper.toEntity(dto));
    }
	
	@Override
	public List<SituacaoDTO> listaSituacoes() {
		return this.situacaoCancelamentoMapper.toDto(this.situacaoCancelamentoRepository.findAll());
	}


	private UsuarioLogin recuperaUsuario() {
		UsuarioLogin usuario = usuarioLogadoService.recuperaUsuarioLogado();
		return usuario;
	}
	
	private SituacaoSolicitacaoDTO preSalvaSituacao(SolicitacaoCancelamentoDTO dto) throws MessagingException {
		
		Long situacaoSolicitacao = dto.getDescricaoMotivo() == null ? 
				SituacaoCancelamentoEnum.AGUARDANDO_ATENDIMENTO.getCodigo() : 
					SituacaoCancelamentoEnum.DEFERIDO.getCodigo();
		SituacaoSolicitacaoDTO situacao = new SituacaoSolicitacaoDTO();
		
		situacao.setRecolhimento(dto.getRecolhimento());
		situacao.setSolicitacao(dto);
		situacao.setSituacao(buscaSituacaoCancelamento(situacaoSolicitacao));
		situacao.setDhInclusao(LocalDateTime.now());
		situacao.setUsuarioInclusao(recuperaUsuario().getMatricula());
		
        return this.salvarSituacaoSolicitacao(this.situacaoSolicitacaoMapper.toEntity(situacao));
	}

	private SituacaoSolicitacaoDTO salvarSituacaoSolicitacao(SituacaoSolicitacao situacao) throws MessagingException {
		
		SituacaoSolicitacao resultado = this.situacaoSolicitacaoRepository.save(situacao);
		
		boolean foiDeferido = resultado.getSituacao().getId() == SituacaoCancelamentoEnum.DEFERIDO.getCodigo();
		boolean foiIndeferido = resultado.getSituacao().getId() == SituacaoCancelamentoEnum.INDEFERIDO.getCodigo();
		
		verificaSeFoiDeferidoOuIndeferido(resultado, foiDeferido, foiIndeferido);
		return this.situacaoSolicitacaoMapper.toDto(resultado);
	}

	private void verificaSeFoiDeferidoOuIndeferido(SituacaoSolicitacao resultado, boolean foiDeferido,
			boolean foiIndeferido) throws MessagingException {
		if(foiDeferido) {
			
			this.recolhimentoService.atualizaSituacao(
					resultado.getRecolhimento().getId(), SituacaoRecolhimentoEnum.CANCELADO.getCodigo());
		} else if(foiIndeferido) {
			
			String destinatarios = "dilver@prf.gov.br";
			String assunto = "Indeferido a Solicitação do Recolhimento " + resultado.getRecolhimento().getDrv();
			String conteudo = conteudoEmail(resultado);
			
			this.enviaEmailService.gerandoEmail(destinatarios, assunto, conteudo);
		}
	}

	private String conteudoEmail(SituacaoSolicitacao resultado) {
		String dataHora = DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm:ss").format(resultado.getDhInclusao());
		return "A Solicitação de Concelamento do recolhimento " + resultado.getRecolhimento().getDrv()
			+ " - foi indeferido no dia " + dataHora
			+ " pelo gestor " + recuperaUsuario().getMatricula() + " " + recuperaUsuario().getNome() + "."
			+ "<br /><br />"
			+ "Solicitação: " + resultado.getSolicitacao().getDescricaoSolicitacao()
			+ "<br /><br />"
			+ "Indeferido: " + resultado.getJustificativa()
			+ "<br /><br /><br />"
			+ "Esse é uma mensagem automática, por favor, não responda.";
	}

	private SituacaoDTO buscaSituacaoCancelamento(Long situacao) {
		Long situacaoCancelamento = verificaSituacaoCancelamento(situacao);
		Optional<SituacaoCancelamento> resultSituacao = this.situacaoCancelamentoRepository
				.findById(situacaoCancelamento);
		SituacaoDTO situacaoDTO = resultSituacao
				.map(optional -> this.situacaoCancelamentoMapper.toDto(optional))
				.orElseThrow(EntityNotFoundException::new);
		return situacaoDTO;
	}

	private Long verificaSituacaoCancelamento(Long situacao) {
		
		Long situacaoSolicitacao = SituacaoCancelamentoEnum.AGUARDANDO_ATENDIMENTO.getCodigo();
		
		if(situacao != null) {
			if(this.isDeferido(situacao)) {
				situacaoSolicitacao = SituacaoCancelamentoEnum.DEFERIDO.getCodigo();
			} else if(this.isIndeferido(situacao)) {
				situacaoSolicitacao = SituacaoCancelamentoEnum.INDEFERIDO.getCodigo();
			}
		}
		return situacaoSolicitacao;
	}

	private boolean isIndeferido(Long situacao) {
		return situacao.equals(SituacaoCancelamentoEnum.INDEFERIDO.getCodigo());
	}

	private boolean isDeferido(Long situacao) {
		return situacao.equals(SituacaoCancelamentoEnum.DEFERIDO.getCodigo());
	}

	protected SituacaoSolicitacaoDTO construirRetorno(SituacaoSolicitacao solicitacao) {
        return this.situacaoSolicitacaoMapper.toDto(solicitacao);
    }
	
}
