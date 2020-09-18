package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.Patio;
import br.gov.prf.silver.domain.ResponsavelPatio;
import br.gov.prf.silver.repository.ResponsavelPatioRepository;
import br.gov.prf.silver.service.ResponsavelPatioService;
import br.gov.prf.silver.service.dto.ResponsavelPatioDTO;
import br.gov.prf.silver.service.mapper.ResponsavelPatioMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */
@Service
@Transactional
public class ResponsavelPatioServiceImpl implements ResponsavelPatioService {

	@Autowired
    private ResponsavelPatioRepository responsavelPatioRepository;

	@Autowired
    private ResponsavelPatioMapper responsavelPatioMapper;

	@Override
    public List<ResponsavelPatioDTO> salvarResponsavelPatio(List<ResponsavelPatioDTO> responsavelPatioList, Patio patio) {
        List<ResponsavelPatio> responsaveisPatioCollection = new ArrayList<>();
        for (ResponsavelPatioDTO dto: responsavelPatioList) {
        	ResponsavelPatio responsavelPatio = responsavelPatioMapper.toEntity(dto);
        	dataHoraAcao(responsavelPatio);
        	responsavelPatio.setPatio(patio);
            this.responsavelPatioRepository.save(responsavelPatio);
            responsaveisPatioCollection.add(responsavelPatio);
        }
        return responsavelPatioMapper.toDto(responsaveisPatioCollection);
    }

	private void dataHoraAcao(ResponsavelPatio responsavelPatio) {
		if(responsavelPatio.getId() == null) {
			responsavelPatio.setDhInclusao(LocalDateTime.now());
		}
	}

	@Override
	public List<ResponsavelPatioDTO> consultarResponsavelPatioPeloIdDoPatio(Long idPatio) {
		List<ResponsavelPatio> responsavelPatio = responsavelPatioRepository.findAllByPatioId(idPatio);
        return responsavelPatioMapper.toDto(responsavelPatio);
	}
	
	@Override
	public void deletarResponsavelPatio(@Valid Long id) {
		responsavelPatioRepository.deleteById(id);
	}

}
