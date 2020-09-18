package br.gov.prf.silver.service;

import java.io.IOException;
import java.util.List;

import br.gov.prf.silver.domain.recolhimento.AutoInfracao;
import br.gov.prf.silver.domain.recolhimento.VeiculoAutoInfracao;
import br.gov.prf.silver.exceptions.BusinessException;
import br.gov.prf.silver.service.dto.CategoriaVeiculoDTO;
import br.gov.prf.silver.service.dto.CorVeiculoDTO;
import br.gov.prf.silver.service.dto.EmplacamentoVeiculoDTO;
import br.gov.prf.silver.service.dto.EspecieVeiculoDTO;
import br.gov.prf.silver.service.dto.EstadoVeiculoDTO;
import br.gov.prf.silver.service.dto.MarcaVeiculoDTO;
import br.gov.prf.silver.service.dto.NivelCombustivelDTO;
import br.gov.prf.silver.service.dto.TipoImagemVeiculoDTO;
import br.gov.prf.silver.service.dto.TipoVeiculoDTO;
import br.gov.prf.silver.service.dto.VeiculoDTO;

/**
 * Review by bruno.abreu.prestador on November/2019
 */
public interface VeiculoService {
    
	VeiculoDTO salvaVeiculo(VeiculoDTO veiculo, EstadoVeiculoDTO estadoVeiculo);
	
	VeiculoDTO consultaPorId(Long id);

	AutoInfracao consultaAutoInfracao(String numeroAuto) throws IOException, BusinessException;

	VeiculoAutoInfracao consultaVeiculoPorPlaca(String placa) throws IOException, BusinessException;

	VeiculoAutoInfracao consultaVeiculoPorChassi(String chassi) throws IOException, BusinessException;

	List<TipoVeiculoDTO> recuperaTipoVeiculo();
	
	List<NivelCombustivelDTO> recuperaNivelCombustivel();

	List<TipoImagemVeiculoDTO> recuperaTipoImagemVeiculo();

	List<MarcaVeiculoDTO> recuperaMarcaVeiculo();
	
	List<CorVeiculoDTO> recuperaCorVeiculo();

	List<EspecieVeiculoDTO> recuperaEspecieVeiculo();

	List<CategoriaVeiculoDTO> recuperaCategoriaVeiculo();

	List<EmplacamentoVeiculoDTO> recuperaEmplacamentoVeiculo();

}
