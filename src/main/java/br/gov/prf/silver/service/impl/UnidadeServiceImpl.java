package br.gov.prf.silver.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.gov.prf.silver.service.UnidadeService;
import br.gov.prf.silver.service.dto.UnidadeDTO;
import br.gov.prf.wsclient.servo2.WSServo2;
import br.gov.prf.wsclient.servo2.dominio.Unidade;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */

@Service
@Transactional
public class UnidadeServiceImpl implements UnidadeService {

	@Override
    public List<UnidadeDTO> buscarUnidadesPelaRegional(Long idRegional) {
		List<UnidadeDTO> unidadeList = new ArrayList<>();
		List<Unidade> unidades = WSServo2.getWSUnidadeOrganizacional()
				.buscarFilhasTipo(idRegional, Unidade.CONST_TIPO_DELEGACIA);
		
		for(Unidade unidade : unidades) {
			UnidadeDTO dto = new UnidadeDTO();
			dto.setId(unidade.getId());
			dto.setNome(unidade.getNome());
			dto.setSigla(unidade.getSigla());
			unidadeList.add(dto);
		}
		return unidadeList;
	}
	
	@Override
    public UnidadeDTO buscarUnidade(Long id) {
		Unidade unidade = WSServo2.getWSUnidadeOrganizacional().find(id);//buscarPaiTipo(id, Unidade.CONST_TIPO_DELEGACIA);
		UnidadeDTO dto = new UnidadeDTO();
		dto.setId(unidade.getId());
		dto.setNome(unidade.getNome());
		dto.setSigla(unidade.getSigla());
		dto.setUnidadePai(unidade.getUnidadePai().getId());
		return dto;
    }

}
