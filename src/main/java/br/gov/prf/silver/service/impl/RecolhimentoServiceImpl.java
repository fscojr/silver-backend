package br.gov.prf.silver.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.gov.dprf.seguranca.dominio.UsuarioLogin;
import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.domain.recolhimento.SituacaoRecolhimento;
import br.gov.prf.silver.enums.SituacaoRecolhimentoEnum;
import br.gov.prf.silver.repository.RecolhimentoRepository;
import br.gov.prf.silver.repository.SituacaoRecolhimentoRepository;
import br.gov.prf.silver.repository.custom.RecolhimentoRepositoryCustom;
import br.gov.prf.silver.repository.specification.RecolhimentoSpecification;
import br.gov.prf.silver.service.CombinacaoVeiculoService;
import br.gov.prf.silver.service.CondutorVeiculoService;
import br.gov.prf.silver.service.EstadoVeiculoService;
import br.gov.prf.silver.service.LocalRecolhimentoService;
import br.gov.prf.silver.service.MotivoAutoService;
import br.gov.prf.silver.service.PertenceService;
import br.gov.prf.silver.service.PolicialRecolhimentoService;
import br.gov.prf.silver.service.RecolhimentoService;
import br.gov.prf.silver.service.RemocaoVeiculoService;
import br.gov.prf.silver.service.UsuarioLogadoService;
import br.gov.prf.silver.service.VeiculoService;
import br.gov.prf.silver.service.dto.CondutorVeiculoDTO;
import br.gov.prf.silver.service.dto.DadosRelatorioPesquisaDTO;
import br.gov.prf.silver.service.dto.EstadoVeiculoDTO;
import br.gov.prf.silver.service.dto.LocalRecolhimentoDTO;
import br.gov.prf.silver.service.dto.PolicialRecolhimentoDTO;
import br.gov.prf.silver.service.dto.RecolhimentoDTO;
import br.gov.prf.silver.service.dto.RemocaoVeiculoDTO;
import br.gov.prf.silver.service.dto.SituacaoDTO;
import br.gov.prf.silver.service.dto.VeiculoDTO;
import br.gov.prf.silver.service.filtro.RecolhimentoFiltro;
import br.gov.prf.silver.service.mapper.CondutorVeiculoMapper;
import br.gov.prf.silver.service.mapper.LocalRecolhimentoMapper;
import br.gov.prf.silver.service.mapper.PolicialRecolhimentoMapper;
import br.gov.prf.silver.service.mapper.RecolhimentoMapper;
import br.gov.prf.silver.service.mapper.RemocaoVeiculoMapper;
import br.gov.prf.silver.service.mapper.SituacaoRecolhimentoMapper;
import br.gov.prf.silver.service.mapper.VeiculoMapper;
import br.gov.prf.wsclient.servo2.WSServo2;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */

@Service
@Transactional
public class RecolhimentoServiceImpl implements RecolhimentoService {

	public static Long VEICULO_NO_PATIO = 1L;

	@Autowired private CondutorVeiculoService condutorVeiculoService;
	@Autowired private LocalRecolhimentoService localRecolhimentoService;
	@Autowired private RemocaoVeiculoService remocaoVeiculoService;
	@Autowired private PertenceService pertenceService;
	@Autowired private VeiculoService veiculoService;
	@Autowired private EstadoVeiculoService estadoVeiculoService;
	@Autowired private CombinacaoVeiculoService combinacaoVeiculoService;
	@Autowired private MotivoAutoService motivoAutoService;
	@Autowired private PolicialRecolhimentoService policialRecolhimentoService;
	@Autowired private UsuarioLogadoService usuarioLogadoService;
	@Autowired private RecolhimentoRepository recolhimentoRepository;
	@Autowired private RecolhimentoRepositoryCustom recolhimentoRepositoryCustom;
	@Autowired private SituacaoRecolhimentoRepository situacaoRecolhimentoRepository;
    @Autowired private VeiculoMapper veiculoMapper;
    @Autowired private RecolhimentoMapper recolhimentoMapper;
    @Autowired private LocalRecolhimentoMapper localRecolhimentoMapper;
    @Autowired private RemocaoVeiculoMapper remocaoVeiculoMapper;
    @Autowired private CondutorVeiculoMapper condutorVeiculoMapper;
    @Autowired private PolicialRecolhimentoMapper policialRecolhimentoMapper;
    @Autowired private SituacaoRecolhimentoMapper situacaoRecolhimentoMapper;
    //@Autowired private ImagemVeiculoService imagemVeiculoService;

    
    @Override
    public RecolhimentoDTO salva(RecolhimentoDTO dto) {
    	
    	Recolhimento recolhimento = recolhimentoMapper.toEntity(dto);

    	salvarVeiculo(dto, recolhimento);
    	salvarCondutorELocalidadeERemocao(dto, recolhimento);
    	
        recolhimento.setProvidenciasRestituicao(dto.getProvidenciasRestituicao());
        recolhimento.setDataRecolhimento(dto.getDataRecolhimento());
        recolhimento.setHoraRecolhimento(dto.getHoraRecolhimento());
        recuperaSequenciaRecolhimento(dto, recolhimento);
        recolhimento.setDrv(gerarNumeroRecolhimento(dto));
        recuperarSituacaoRecolhimentoPersistente(recolhimento);
        recolhimento.setJustificativaRemocao(dto.getRemocaoVeiculo().getDsJustificativaRemocao());
        this.recolhimentoRepository.save(recolhimento);
        dto.setId(recolhimento.getId());

        motivoAutoService.salvar(dto.getMotivos(), recolhimento);
        validaPolicialAuxiliar(dto, recolhimento);
        policialRecolhimentoService.salvarPolicialResponsavel(recolhimento);
        
        return dto;
    }

	private void validaPolicialAuxiliar(RecolhimentoDTO dto, Recolhimento recolhimento) {
		if(dto.getPrfAuxiliares() != null) {
			policialRecolhimentoService.salvarPolicialAuxilizar(dto.getPrfAuxiliares(), recolhimento);
		}
	}

    @Override
	public Page<RecolhimentoDTO> consulta(RecolhimentoFiltro filtro, Pageable pageable) {
    	Specification<Recolhimento> especificacao = Specification
    			.where(RecolhimentoSpecification.filtroPesquisa(filtro));
        return recolhimentoRepository.findAll(especificacao, pageable).map(this::construirRetorno);
	}

    @Override
	public RecolhimentoDTO consultaPorId(Long id) {
		Optional<Recolhimento> recolhimento = recolhimentoRepository.findById(id);
		RecolhimentoDTO recolhimentoDTO = recolhimento
				.map(optional -> recolhimentoMapper.toDto(optional))
				.orElseThrow(EntityNotFoundException::new);
        return recolhimentoDTO;
	}

    @Override
    public List<RecolhimentoDTO> consultaVeiculoSemLiberacao(String placa) {
    	RecolhimentoFiltro filtro = new RecolhimentoFiltro();
    	filtro.setPlaca(placa);
    	filtro.setSituacao(ajustandoSituacoesSemLiberacao());
    	List<RecolhimentoDTO> dados = buscaDadosRecolhimento(filtro);
    	return dados;
    }

	@Override
	public RecolhimentoDTO consultaPeloDrv(String drv) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override	
	public List<SituacaoDTO> consultaSituacaoRecolhimento() {
		List<SituacaoRecolhimento> situacao = situacaoRecolhimentoRepository.findAll();
		return situacaoRecolhimentoMapper.toDto(situacao);
	}
	
	@Override
	public List<DadosRelatorioPesquisaDTO> recuperaDadosRelatorio(RecolhimentoFiltro filtro) {
		List<RecolhimentoDTO> dadosRelatorioDTO = buscaDadosRecolhimento(filtro);
		List<DadosRelatorioPesquisaDTO> dadosCollection = ajustaDadosRelatorio(dadosRelatorioDTO);
		return dadosCollection;
	}
	
	@Override
	public Optional<SituacaoRecolhimento> recuperaSituacao(Long situacao) {
		Optional<SituacaoRecolhimento> optional = this.situacaoRecolhimentoRepository.findById(situacao);
		return optional;
	}
	
	@Override
	public void atualizaSituacao(Long recolhimentoId, Long situacao) {
		
		RecolhimentoDTO dto = this.consultaPorId(recolhimentoId);
		Recolhimento recolhimento = this.recolhimentoMapper.toEntity(dto); 
        recuperarSituacaoRecolhimentoPersistente(recolhimento);
        
        this.recolhimentoRepository.save(recolhimento);
	}

	
	private UsuarioLogin recuperaUsuario() {
		UsuarioLogin usuario = usuarioLogadoService.recuperaUsuarioLogado();
		return usuario;
	}
	
	private String matriculaNomePolicial() {
		return this.recuperaUsuario().getMatricula() + " - " + this.recuperaUsuario().getNome();
	}
	
	private List<RecolhimentoDTO> buscaDadosRecolhimento(RecolhimentoFiltro filtro) {
		List<Recolhimento> dadosRelatorio = recolhimentoRepositoryCustom
				.obterListaRecolhimentoPorParametro(filtro);
		List<RecolhimentoDTO> dadosRelatorioDTO = recolhimentoMapper.toDto(dadosRelatorio);
		return dadosRelatorioDTO;
	}


	private void recuperarSituacaoRecolhimentoPersistente(Recolhimento recolhimento) {
		
		Long situacaoRecolhimento = verificaSituacaoRecolhimento(recolhimento);
		Optional<SituacaoRecolhimento> optional = recuperaSituacao(situacaoRecolhimento);
		optional.ifPresent(recolhimento::setSituacao);
	}

	private Long verificaSituacaoRecolhimento(Recolhimento recolhimento) {
		Long situacaoRecolhimento = SituacaoRecolhimentoEnum.VEICULO_NO_PATIO.getCodigo();
		
		if(recolhimento.getSituacao() != null) {
			situacaoRecolhimento = recolhimento.getSituacao().getId();
		}
		return situacaoRecolhimento;
	}

	protected RecolhimentoDTO construirRetorno(Recolhimento recolhimento) {
		List<PolicialRecolhimentoDTO> policial = 
				policialRecolhimentoMapper.toDto(recolhimento.getPrfResponsavel());
    	RecolhimentoDTO dto = recolhimentoMapper.toDto(recolhimento);
    	dto.setResponsavel(policial.get(0));
        return dto;
    }

	private String gerarNumeroRecolhimento(RecolhimentoDTO recolhimento) {
        String numero = "";

        // 2 DIGITOS DA UF DO RECOLHIMENTO
        numero = numero.concat(recolhimento.getLocalRecolhimento().getUfSigla().toUpperCase());
        // 2 DIGITOS DA IDENTIFICAÇÃO DA DELEGACIA
        numero = numero.concat(ajustaNumRefRegionalRecolhimento(recolhimento));
        // 6 DIGITOS DA DATA INVERTIDA YYMMDD
        numero = numero.concat(String.valueOf(recolhimento.getDataRecolhimento().getYear()).substring(2)); // Ano
        numero = numero.concat(String.format("%02d" ,recolhimento.getDataRecolhimento().getMonthValue())); // Mês
        numero = numero.concat(String.format("%02d" ,recolhimento.getDataRecolhimento().getDayOfMonth())); // Dia
        // 4 DIGITOS DA DATA HORA HHMM
        numero = numero.concat(String.format("%02d" ,recolhimento.getHoraRecolhimento().getHour()));       // Hora
        numero = numero.concat(String.format("%02d" ,recolhimento.getHoraRecolhimento().getMinute()));     // Minutos
        // 3 DIGITOS DA SEQUENCIA
        numero = numero.concat(String.format("%03d", recolhimento.getSequencia()));	   // Sequência
        return numero;
    }

	private String ajustaNumRefRegionalRecolhimento(RecolhimentoDTO recolhimento) {
		return String.format("%02d", Integer.valueOf(WSServo2.getWSUnidadeOrganizacional()
        		.find(recolhimento.getLocalRecolhimento().getUnidade())
        		.getDelegacia().getSigla().replaceAll("[^0-9]","")));
	}

	private List<DadosRelatorioPesquisaDTO> ajustaDadosRelatorio(
			List<RecolhimentoDTO> dadosRelatorioDTO) {
		DadosRelatorioPesquisaDTO dadosDto = new DadosRelatorioPesquisaDTO();
		dadosDto.setPolicial(this.matriculaNomePolicial());
        dadosDto.setDadosRelatorioList(dadosRelatorioDTO);
        List<DadosRelatorioPesquisaDTO> dadosCollection = new ArrayList<>();
        dadosCollection.add(dadosDto);
		return dadosCollection;
	}

	private void salvarCondutorELocalidadeERemocao(RecolhimentoDTO dto, Recolhimento recolhimento) {
		
		LocalRecolhimentoDTO localRecolhimento;
		RemocaoVeiculoDTO remocaoVeiculo;
		CondutorVeiculoDTO condutorVeiculo;
		
		if(dto.getLocalRecolhimento() != null) {
	    	localRecolhimento = localRecolhimentoService
	    			.salvarLocalRecolhimento(dto.getLocalRecolhimento());
        	recolhimento.setLocalRecolhimento(localRecolhimentoMapper.toEntity(localRecolhimento));
    	}

    	if(!dto.getRemocaoVeiculo().equals(null)) {
    		remocaoVeiculo = remocaoVeiculoService
	    			.salvarRemocao(dto.getRemocaoVeiculo());
        	recolhimento.setRemocaoVeiculo(remocaoVeiculoMapper.toEntity(remocaoVeiculo));
    	}

    	if(dto.getCondutorVeiculo().getTipo().getId() != 3L) {
	    	condutorVeiculo = condutorVeiculoService
	    			.salvarCondutor(dto.getCondutorVeiculo());
        	recolhimento.setCondutorVeiculo(condutorVeiculoMapper.toEntity(condutorVeiculo));
    	}
	}

	private void salvarVeiculo(RecolhimentoDTO dto, Recolhimento recolhimento) {
		VeiculoDTO veiculo;
    	EstadoVeiculoDTO estadoVeiculo = new EstadoVeiculoDTO();

		if(dto.getVeiculo().getEstadoVeiculo().getHodometro() != null
    			|| dto.getVeiculo().getEstadoVeiculo().getPossuiHodometro().equals(true)) {
	    	estadoVeiculo = estadoVeiculoService.salvar(dto.getVeiculo().getEstadoVeiculo());
    	}

    	veiculo = veiculoService.salvaVeiculo(dto.getVeiculo(), estadoVeiculo);
    	recolhimento.setVeiculo(veiculoMapper.toEntity(veiculo));
    	
    	if(!dto.getImagensEstado().isEmpty()) {
    		//imagemVeiculoService.saveAll(dto.getImagensEstado(), estadoVeiculo);
    	}
    	
    	if(!dto.getCombinacaoList().isEmpty()) {
    		combinacaoVeiculoService.salvarCombinacao(dto.getCombinacaoList(), veiculo);
    	}
    	
		if(!dto.getPertenceList().isEmpty()) {
			pertenceService.salvar(dto.getPertenceList(), veiculo, null);
    	}
	}

	private void recuperaSequenciaRecolhimento(RecolhimentoDTO dto, Recolhimento recolhimento) {
		Long sequencia = this.recolhimentoRepository.maxSequencia();
		if(sequencia == null || (sequencia == 999)) { 
			recolhimento.setSequencia(500L);
	        dto.setSequencia(recolhimento.getSequencia());
		} else {
			recolhimento.setSequencia(++sequencia);
			dto.setSequencia(recolhimento.getSequencia());
		}
	}
	
	private List<Long> ajustandoSituacoesSemLiberacao() {
		List<Long> situacoesSemLiberacao = new ArrayList<>();
    	situacoesSemLiberacao.add(SituacaoRecolhimentoEnum.VEICULO_NO_PATIO.getCodigo());
    	situacoesSemLiberacao.add(SituacaoRecolhimentoEnum.EM_PROCESSO_DE_LEILAO.getCodigo());
		situacoesSemLiberacao.add(SituacaoRecolhimentoEnum.LIBERACAO_BLOQUEADA.getCodigo());
		situacoesSemLiberacao.add(SituacaoRecolhimentoEnum.ENCAMINHADO_PATIO_CONVENIADO.getCodigo());
		return situacoesSemLiberacao;
	}

}
