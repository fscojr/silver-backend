package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.Pais;
import br.gov.prf.silver.domain.Veiculo;
import br.gov.prf.silver.domain.recolhimento.AutoInfracao;
import br.gov.prf.silver.domain.recolhimento.CategoriaVeiculo;
import br.gov.prf.silver.domain.recolhimento.CorVeiculo;
import br.gov.prf.silver.domain.recolhimento.EmplacamentoVeiculo;
import br.gov.prf.silver.domain.recolhimento.EspecieVeiculo;
import br.gov.prf.silver.domain.recolhimento.MarcaVeiculo;
import br.gov.prf.silver.domain.recolhimento.TipoVeiculo;
import br.gov.prf.silver.domain.recolhimento.VeiculoAutoInfracao;
import br.gov.prf.silver.exceptions.BusinessException;
import br.gov.prf.silver.repository.CategoriaVeiculoRepository;
import br.gov.prf.silver.repository.CorVeiculoRepository;
import br.gov.prf.silver.repository.EmplacamentoVeiculoRepository;
import br.gov.prf.silver.repository.EspecieVeiculoRepository;
import br.gov.prf.silver.repository.MarcaVeiculoRepository;
import br.gov.prf.silver.repository.NivelCombustivelRepository;
import br.gov.prf.silver.repository.PaisRepository;
import br.gov.prf.silver.repository.TipoImagemVeiculoRepository;
import br.gov.prf.silver.repository.TipoVeiculoRepository;
import br.gov.prf.silver.repository.VeiculoRepository;
import br.gov.prf.silver.service.BarramentoService;
import br.gov.prf.silver.service.MotorConsultaService;
import br.gov.prf.silver.service.RecolhimentoService;
import br.gov.prf.silver.service.VeiculoService;
import br.gov.prf.silver.service.dto.CategoriaVeiculoDTO;
import br.gov.prf.silver.service.dto.CorVeiculoDTO;
import br.gov.prf.silver.service.dto.EmplacamentoVeiculoDTO;
import br.gov.prf.silver.service.dto.EspecieVeiculoDTO;
import br.gov.prf.silver.service.dto.EstadoVeiculoDTO;
import br.gov.prf.silver.service.dto.MarcaVeiculoDTO;
import br.gov.prf.silver.service.dto.NivelCombustivelDTO;
import br.gov.prf.silver.service.dto.RecolhimentoDTO;
import br.gov.prf.silver.service.dto.TipoImagemVeiculoDTO;
import br.gov.prf.silver.service.dto.TipoVeiculoDTO;
import br.gov.prf.silver.service.dto.VeiculoDTO;
import br.gov.prf.silver.service.mapper.CategoriaVeiculoMapper;
import br.gov.prf.silver.service.mapper.CorVeiculoMapper;
import br.gov.prf.silver.service.mapper.EmplacamentoVeiculoMapper;
import br.gov.prf.silver.service.mapper.EspecieVeiculoMapper;
import br.gov.prf.silver.service.mapper.MarcaVeiculoMapper;
import br.gov.prf.silver.service.mapper.NivelCombustivelMapper;
import br.gov.prf.silver.service.mapper.TipoImagemVeiculoMapper;
import br.gov.prf.silver.service.mapper.TipoVeiculoMapper;
import br.gov.prf.silver.service.mapper.VeiculoMapper;

@Service
public class VeiculoServiceImpl implements VeiculoService {

    private VeiculoRepository veiculoRepository;
    private CategoriaVeiculoRepository categoriaVeiculoRepository;
    private MarcaVeiculoRepository marcaVeiculoRepository;
    private CorVeiculoRepository corVeiculoRepository;
    private EspecieVeiculoRepository especieVeiculoRepository;
    private EmplacamentoVeiculoRepository emplacamentoVeiculoRepository;
    private TipoVeiculoRepository tipoVeiculoRepository;
    private NivelCombustivelRepository nivelCombustivelRepository;
    private TipoImagemVeiculoRepository tipoImagemVeiculoRepository;
    private PaisRepository paisRepository;
    private VeiculoMapper veiculoMapper;
    private TipoVeiculoMapper tipoVeiculoMapper;
    private NivelCombustivelMapper nivelCombustivelMapper;
    private MarcaVeiculoMapper marcaVeiculoMapper;
    private CorVeiculoMapper corVeiculoMapper;
    private TipoImagemVeiculoMapper tipoImagemVeiculoMapper;
    private EspecieVeiculoMapper especieVeiculoMapper;
    private CategoriaVeiculoMapper categoriaVeiculoMapper;
    private EmplacamentoVeiculoMapper emplacamentoVeiculoMapper;
    private RecolhimentoService recolhimentoService;
    private BarramentoService barramentoService;
    private MotorConsultaService motorConsultaService;
    private MessageSource messageSource;
    
    
    @Autowired
    public VeiculoServiceImpl (
    		VeiculoRepository veiculoRepository,              
    		CategoriaVeiculoRepository categoriaVeiculoRepository,
    		MarcaVeiculoRepository marcaVeiculoRepository, 
    		CorVeiculoRepository corVeiculoRepository,        
    		EspecieVeiculoRepository especieVeiculoRepository,    
    		EmplacamentoVeiculoRepository emplacamentoVeiculoRepository,
    		TipoVeiculoRepository tipoVeiculoRepository,
    		NivelCombustivelRepository nivelCombustivelRepository,
    		TipoImagemVeiculoRepository tipoImagemVeiculoRepository,
    		PaisRepository paisRepository,
    		VeiculoMapper veiculoMapper,                        
    		TipoVeiculoMapper tipoVeiculoMapper,
    		NivelCombustivelMapper nivelCombustivelMapper,        
    		MarcaVeiculoMapper marcaVeiculoMapper,        
    		CorVeiculoMapper corVeiculoMapper,            
    		TipoImagemVeiculoMapper tipoImagemVeiculoMapper,
    		EspecieVeiculoMapper especieVeiculoMapper,      
    		CategoriaVeiculoMapper categoriaVeiculoMapper,        
    		EmplacamentoVeiculoMapper emplacamentoVeiculoMapper,  
    		RecolhimentoService recolhimentoService,
    		BarramentoService barramentoService,
    		MotorConsultaService motorConsultaService,
    		MessageSource messageSource) {
    	this.veiculoRepository = veiculoRepository;                              
    	this.categoriaVeiculoRepository = categoriaVeiculoRepository;            
    	this.marcaVeiculoRepository = marcaVeiculoRepository;                    
    	this.corVeiculoRepository = corVeiculoRepository;                        
    	this.especieVeiculoRepository = especieVeiculoRepository;                
    	this.emplacamentoVeiculoRepository = emplacamentoVeiculoRepository;      
    	this.tipoVeiculoRepository = tipoVeiculoRepository;                      
    	this.nivelCombustivelRepository = nivelCombustivelRepository;            
    	this.tipoImagemVeiculoRepository = tipoImagemVeiculoRepository;          
    	this.paisRepository = paisRepository;                                    
    	this.veiculoMapper = veiculoMapper;                                      
    	this.tipoVeiculoMapper = tipoVeiculoMapper;                              
    	this.nivelCombustivelMapper = nivelCombustivelMapper;                    
    	this.marcaVeiculoMapper = marcaVeiculoMapper;                            
    	this.corVeiculoMapper = corVeiculoMapper;                                
    	this.tipoImagemVeiculoMapper = tipoImagemVeiculoMapper;                  
    	this.especieVeiculoMapper = especieVeiculoMapper;                        
    	this.categoriaVeiculoMapper = categoriaVeiculoMapper;                    
    	this.emplacamentoVeiculoMapper = emplacamentoVeiculoMapper;              
    	this.recolhimentoService = recolhimentoService;    
    	this.barramentoService = barramentoService;
    	this.motorConsultaService = motorConsultaService;
    	this.messageSource = messageSource;
    }
    
    
    @Override
    public VeiculoDTO salvaVeiculo(VeiculoDTO dto, EstadoVeiculoDTO estadoVeiculo) {
    	dto.setEstadoVeiculo(estadoVeiculo);
        Veiculo veiculo = veiculoMapper.toEntity(dto);
        recuperaDadoPersistente(dto, veiculo);
        dataAcao(veiculo);
        veiculoRepository.save(veiculo);
        dto.setId(veiculo.getId());
        return dto;
    }
    
    @Override
    public AutoInfracao consultaAutoInfracao(String numeroAuto) throws BusinessException {
        AutoInfracao autoInfracao = 
    		barramentoService.consultaAutoInfracao(numeroAuto.toUpperCase())
    			.filter(autoInfracaoVO -> Objects.nonNull(autoInfracaoVO))
    			.orElseThrow(() -> new BusinessException(
    					messageSource.getMessage("MSG003", null, LocaleContextHolder.getLocale())));
        
        VeiculoAutoInfracao veiculo = consultaVeiculoPorPlaca(autoInfracao.getPlaca().toUpperCase());
        autoInfracao.setVeiculo(veiculo);
        return autoInfracao;
    }

    @Override
    public VeiculoDTO consultaPorId(Long id) {
        Optional<Veiculo> veiculo = veiculoRepository.findById(id);
        VeiculoDTO veiculoDTO = veiculo
            .map(optional -> veiculoMapper.toDto(optional))
            .orElseThrow(EntityNotFoundException::new);
        return veiculoDTO;
    }

    @Override
    public VeiculoAutoInfracao consultaVeiculoPorChassi(String chassi) throws BusinessException {
    	
		VeiculoAutoInfracao veiculo =
    			motorConsultaService.consultaVeiculo(chassi, "CHASSI")
	    			.filter(veiculoai -> Objects.nonNull(veiculoai))
	    			.orElseThrow(() -> new BusinessException(
	    					messageSource.getMessage("MSG003", null, LocaleContextHolder.getLocale())));
        	
    	List<RecolhimentoDTO> recolhimento = buscaVeiculoRegistrado(veiculo.getPlaca().toUpperCase());
        veiculo.setRecolhimento(recolhimento);
        
        return veiculo;
    }

	@Override
    public VeiculoAutoInfracao consultaVeiculoPorPlaca(String placa) throws BusinessException {
       
    	VeiculoAutoInfracao veiculo = 
    			motorConsultaService.consultaVeiculo(placa, "PLACA")
	    			.filter(veiculoai -> Objects.nonNull(veiculoai))
	    			.orElseThrow(() -> new BusinessException(
	    					messageSource.getMessage("MSG003", null, LocaleContextHolder.getLocale())));
        	
    	List<RecolhimentoDTO> recolhimento = buscaVeiculoRegistrado(placa.toUpperCase());
        veiculo.setRecolhimento(recolhimento);
        
        return veiculo;
    }

    @Override
    public List<TipoVeiculoDTO> recuperaTipoVeiculo() {
        return tipoVeiculoMapper.toDto(tipoVeiculoRepository.findAll());
    }

    @Override
    public List<NivelCombustivelDTO> recuperaNivelCombustivel() {
        return nivelCombustivelMapper.toDto(nivelCombustivelRepository.findAll());
    }

    @Override
    public List<TipoImagemVeiculoDTO> recuperaTipoImagemVeiculo() {
        return tipoImagemVeiculoMapper.toDto(tipoImagemVeiculoRepository.findAll());
    }

    @Override
    public List<MarcaVeiculoDTO> recuperaMarcaVeiculo() {
        return marcaVeiculoMapper.toDto(marcaVeiculoRepository.findAll());
    }

    @Override
    public List<CorVeiculoDTO> recuperaCorVeiculo() {
        return corVeiculoMapper.toDto(corVeiculoRepository.findAll());
    }

    @Override
    public List<EspecieVeiculoDTO> recuperaEspecieVeiculo() {
        return especieVeiculoMapper.toDto(especieVeiculoRepository.findAll());
    }

    @Override
    public List<CategoriaVeiculoDTO> recuperaCategoriaVeiculo() {
        return categoriaVeiculoMapper.toDto(categoriaVeiculoRepository.findAll());
    }

    @Override
    public List<EmplacamentoVeiculoDTO> recuperaEmplacamentoVeiculo() {
        return emplacamentoVeiculoMapper.toDto(emplacamentoVeiculoRepository.findAll());
    }
    
    private List<RecolhimentoDTO> buscaVeiculoRegistrado(String placa) {
		return recolhimentoService.consultaVeiculoSemLiberacao(placa);
	}

	private void recuperaDadoPersistente(VeiculoDTO dto, Veiculo veiculo) {
		recuperarTipoPersistente(dto, veiculo);
        recuperarEmplacamentoPersistente(dto, veiculo);
        recuperarCategoriaPersistente(dto, veiculo);
        recuperarMarcaPersistente(dto, veiculo);
        recuperarCorPersistente(dto, veiculo);
        recuperarEspeciePersistente(dto, veiculo);
        recuperarPaisPersistente(dto, veiculo);
	}

	private void dataAcao(Veiculo veiculo) {
		if(veiculo.getId() == null) {
	        veiculo.setAtivo(true);
	        veiculo.setDhInclusao(LocalDateTime.now());
        }
	}

    private void recuperarTipoPersistente(@Valid VeiculoDTO dto, Veiculo veiculo) {
        Optional<TipoVeiculo> optional = tipoVeiculoRepository.findById(dto.getTipoVeiculo().getId());
        optional.ifPresent(veiculo::setTipoVeiculo);
    }

    private void recuperarEmplacamentoPersistente(@Valid VeiculoDTO dto, Veiculo veiculo) {
        Optional<EmplacamentoVeiculo> optional = emplacamentoVeiculoRepository
            .findByDescricaoContainingIgnoreCase(dto.getEmplacamento().getDescricao());
        optional.ifPresent(veiculo::setEmplacamento);
    }

    private void recuperarCategoriaPersistente(@Valid VeiculoDTO dto, Veiculo veiculo) {
        Optional<CategoriaVeiculo> optional = categoriaVeiculoRepository
            .findByDescricaoContainingIgnoreCase(dto.getCategoriaVeiculo().getDescricao());
        optional.ifPresent(veiculo::setCategoriaVeiculo);
    }

    private void recuperarMarcaPersistente(@Valid VeiculoDTO dto, Veiculo veiculo) {
    	Optional<MarcaVeiculo> optional = marcaVeiculoRepository.findById(dto.getMarcaVeiculo().getId());
        optional.ifPresent(veiculo::setMarcaVeiculo);
    }

    private void recuperarCorPersistente(@Valid VeiculoDTO dto, Veiculo veiculo) {
        Optional<CorVeiculo> optional = corVeiculoRepository.findById(dto.getCorVeiculo().getId());
        optional.ifPresent(veiculo::setCorVeiculo);
    }

    private void recuperarEspeciePersistente(@Valid VeiculoDTO dto, Veiculo veiculo) {
        Optional<EspecieVeiculo> optional = especieVeiculoRepository.findById(dto.getEspecieVeiculo().getId());
        optional.ifPresent(veiculo::setEspecieVeiculo);
    }

    private void recuperarPaisPersistente(@Valid VeiculoDTO dto, Veiculo veiculo) {
        //TODO: ajustar id;
        Optional<Pais> optional = paisRepository.findById(31L);
        optional.ifPresent(veiculo::setPais);
    }

}
