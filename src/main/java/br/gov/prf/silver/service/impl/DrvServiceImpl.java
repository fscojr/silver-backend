package br.gov.prf.silver.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.dprf.wsclient.servo.WSServo;
import br.gov.dprf.wsclient.servo.dominio.Municipio;
import br.gov.prf.silver.domain.recolhimento.VeiculoAutoInfracao;
import br.gov.prf.silver.enums.SentidoEnum;
import br.gov.prf.silver.exceptions.BusinessException;
import br.gov.prf.silver.service.CombinacaoVeiculoService;
import br.gov.prf.silver.service.DrvService;
import br.gov.prf.silver.service.MotivoAutoService;
import br.gov.prf.silver.service.PertenceService;
import br.gov.prf.silver.service.PolicialRecolhimentoService;
import br.gov.prf.silver.service.RecolhimentoService;
import br.gov.prf.silver.service.UnidadeService;
import br.gov.prf.silver.service.VeiculoService;
import br.gov.prf.silver.service.dto.CombinacaoDTO;
import br.gov.prf.silver.service.dto.MotivoRecolhimentoDTO;
import br.gov.prf.silver.service.dto.PertenceDTO;
import br.gov.prf.silver.service.dto.PolicialRecolhimentoDTO;
import br.gov.prf.silver.service.dto.RecolhimentoDTO;
import br.gov.prf.silver.service.dto.RelatorioDrvDTO;
import br.gov.prf.silver.service.dto.UnidadeDTO;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */

@Service
@Transactional
public class DrvServiceImpl implements DrvService {

	@Autowired private RecolhimentoService recolhimentoService;
	@Autowired private VeiculoService veiculoService;
	@Autowired private PolicialRecolhimentoService policialRecolhimentoService;
	@Autowired private PertenceService pertenceService;
	@Autowired private MotivoAutoService motivoAutoService;
	@Autowired private CombinacaoVeiculoService combinacaoVeiculoService;
    @Autowired private UnidadeService unidadeService;

    @Autowired 
    public DrvServiceImpl(
    		RecolhimentoService recolhimentoService,                 
    		VeiculoService veiculoService,                           
    		PolicialRecolhimentoService policialRecolhimentoService, 
    		PertenceService pertenceService,                         
    		MotivoAutoService motivoAutoService,                     
    		CombinacaoVeiculoService combinacaoVeiculoService,       
    		UnidadeService unidadeService) {
    	
    	this.recolhimentoService = recolhimentoService;                 
    	this.veiculoService = veiculoService;                           
    	this.policialRecolhimentoService = policialRecolhimentoService; 
    	this.pertenceService = pertenceService;                         
    	this.motivoAutoService = motivoAutoService;                     
    	this.combinacaoVeiculoService = combinacaoVeiculoService;       
    	this.unidadeService = unidadeService;                               	
    }
	
	@Override
	public List<RelatorioDrvDTO> gerarDadosRelatorio(Long id)  throws IOException, BusinessException {
        List<RelatorioDrvDTO> dadosRelatorio = new ArrayList<>();

		RecolhimentoDTO recolhimento = recolhimentoService.consultaPorId(id);
		VeiculoAutoInfracao veiculo = veiculoService
				.consultaVeiculoPorPlaca(recolhimento.getVeiculo().getPlaca());
		List<CombinacaoDTO> combinacao = combinacaoVeiculoService
				.consultarPorVeiculoId(recolhimento.getVeiculo().getId());
		List<PolicialRecolhimentoDTO> policialRecolhimento = policialRecolhimentoService
				.consultarPorRecolhimentoId(recolhimento.getId());
		List<MotivoRecolhimentoDTO> motivoAuto = motivoAutoService
				.buscarPorRecolhimentoId(recolhimento.getId());
		List<PertenceDTO> pertence = pertenceService
				.consultaVeiculoId(recolhimento.getVeiculo().getId());
		
		dadosRelatorio.add(ajustaDtoRelatorio(
				recolhimento, veiculo, motivoAuto, pertence, policialRecolhimento, combinacao));
		
		return dadosRelatorio;
    }
	
	private RelatorioDrvDTO ajustaDtoRelatorio(RecolhimentoDTO recolhimento, VeiculoAutoInfracao veiculo, 
				List<MotivoRecolhimentoDTO> motivoAuto, List<PertenceDTO> pertence, 
				List<PolicialRecolhimentoDTO> policial, List<CombinacaoDTO> combinacao) {
		RelatorioDrvDTO relatorio = new RelatorioDrvDTO();
		
		relatorio.setRecolhimento(recolhimento);
		relatorio.setVeiculo(veiculo);
		relatorio.setCombinacaoList(combinacao);
		relatorio.setCondutor(recolhimento.getCondutorVeiculo());
		recolhimento.getLocalRecolhimento().setSentidoSg(ajustarSentidoSigla(recolhimento));
		relatorio.setLocalRecolhimento(recolhimento.getLocalRecolhimento());
		relatorio.setRegional(buscaNomeRegional(recolhimento.getLocalRecolhimento().getRegional()));
		relatorio.setMunicipioLocalRecolhimento(buscaCodigoNomeMunicipio(recolhimento.getLocalRecolhimento().getMunicipio()));
		relatorio.setMotivoList(motivoAuto);
		relatorio.setPatio(recolhimento.getLocalRecolhimento().getPatio());
		relatorio.setRemocao(recolhimento.getRemocaoVeiculo());
		relatorio.setPertenceList(pertence);
		relatorio.setEstado(recolhimento.getVeiculo().getEstadoVeiculo());
		relatorio.setPolicial(policial.get(0));
		
		return relatorio;
	}

	private String buscaCodigoNomeMunicipio(Long idMunicipio) {
		List<Municipio> buscaMunicipio = WSServo.getInstance().buscaMunicipio(idMunicipio, null, null, null);
		if(!buscaMunicipio.isEmpty()) {
			return buscaMunicipio.get(0).getIdMunicipio() +" - "+ buscaMunicipio.get(0).getNome();
		} else {
			return "-";
		}
	}

	private String buscaNomeRegional(Long idRegional) {
		UnidadeDTO regional = unidadeService.buscarUnidade(idRegional);
		return regional.getNome() +" - "+ regional.getSigla();
	}

	private String ajustarSentidoSigla(RecolhimentoDTO recolhimento) {
		return recolhimento.getLocalRecolhimento().getSentido() == 1 ? 
				SentidoEnum.CRESCENTE.getDescricao() : SentidoEnum.DECRESCENTE.getDescricao();
	}
	
}
