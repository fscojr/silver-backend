package br.gov.prf.silver.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import br.gov.prf.silver.service.RegionalService;
import br.gov.prf.silver.service.dto.RegionalDTO;
import br.gov.prf.wsclient.servo2.consulta.ConsultaUnidade;
import br.gov.prf.wsclient.servo2.dominio.Unidade;
import br.gov.prf.wsclient.servo2.util.OrdenUnidade;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */

@Service
@Transactional
public class RegionalServiceImpl implements RegionalService {

    public List<RegionalDTO> recuperarListaRegionais() throws InterruptedException {
    	List<RegionalDTO> regionalList = new ArrayList<>();
    	ConsultaUnidade consulta = new ConsultaUnidade().setIdTipo(Unidade.CONST_TIPO_REGIONAL);
    	List<Unidade> unidades = consulta.getTudoOrdenado(OrdenUnidade.SIGLA_UF);
    	
    	for(Unidade unidade : unidades) {
    		RegionalDTO regional = new RegionalDTO();
    		regional.setId(unidade.getId());
    		regional.setNome(unidade.getNome());
    		regional.setSigla(unidade.getSigla());
    		regionalList.add(regional);
    	}
    	return regionalList;
    }
	
}
