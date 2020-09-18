package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.dprf.wsclient.servo.WSServo;
import br.gov.dprf.wsclient.servo.dominio.UfBr;
import br.gov.prf.silver.domain.ContratoPatio;
import br.gov.prf.silver.domain.contratoPatio.TrechoCobertura;
import br.gov.prf.silver.repository.TrechoCoberturaRepository;
import br.gov.prf.silver.service.TrechoCoberturaService;
import br.gov.prf.silver.service.dto.TrechoCoberturaDTO;
import br.gov.prf.silver.service.dto.TrechoDTO;
import br.gov.prf.silver.service.mapper.TrechoCoberturaMapper;
import br.gov.prf.wsclient.servo2.WSServo2;
import br.gov.prf.wsclient.servo2.dominio.Trecho;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class TrechoCoberturaServiceImpl implements TrechoCoberturaService {

    @Autowired
    private TrechoCoberturaRepository trechoCoberturaRepository;

    @Autowired
    private TrechoCoberturaMapper trechoCoberturaMapper;


    @Override
    public List<TrechoCoberturaDTO> salvarTrechoCobertura(List<TrechoCoberturaDTO> trechoCoberturaList, ContratoPatio contratoPatio) {

        List<TrechoCobertura> trechoCoberturaCollection = new ArrayList<>();

        for (TrechoCoberturaDTO dto : trechoCoberturaList) {
            TrechoCobertura trechoCobertura = trechoCoberturaMapper.toEntity(dto);
            if (trechoCobertura.getId() == null) {
                trechoCobertura.setAtivo(true);
                trechoCobertura.setDhInclusao(LocalDateTime.now());
                trechoCobertura.setDhAtualizacao(LocalDateTime.now());
            } else {
                trechoCobertura.setDhAtualizacao(LocalDateTime.now());
            }
            trechoCobertura.setContratoPatio(contratoPatio);
            this.trechoCoberturaRepository.save(trechoCobertura);
            trechoCoberturaCollection.add(trechoCobertura);
        }
        return trechoCoberturaMapper.toDto(trechoCoberturaCollection);
    }

    @Override
    public List<TrechoCoberturaDTO> consultarTrechoPorIdDoContrato(Long idContrato) {
        List<TrechoCobertura> trechoCobertura = trechoCoberturaRepository.findAllByContratoPatioId(idContrato);
        return trechoCoberturaMapper.toDto(trechoCobertura);
    }

    @Override
    public void deletarTrechoCobertura(@Valid Long id) {
        trechoCoberturaRepository.deleteById(id);
    }

	@Override
    public List<TrechoDTO> consultar(String uf, String br, Long km) {
		
		List<Trecho> trechoServo = 
				WSServo2.getWSTrecho().buscaTrecho(uf, br, Double.valueOf(km), km);
		List<TrechoDTO> trechoList = new ArrayList<>();

		for(br.gov.prf.wsclient.servo2.dominio.Trecho trecho : trechoServo) {
			TrechoDTO dto = new TrechoDTO();
			dto.setId(trecho.getId());
			dto.setDescricao(trecho.getDescricaoTrecho());
			trechoList.add(dto);
		}
    	return trechoList;
    }

    public List<UfBr> consultaBrPorUf(String uf) {
    	
        return Optional.ofNullable(WSServo.getInstance().buscaBrs(uf)).orElse(new ArrayList<>());
    }

}
