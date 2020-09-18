package br.gov.prf.silver.service.impl.client;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.gov.prf.silver.domain.recolhimento.AutoInfracao;
import br.gov.prf.silver.service.BarramentoService;

/**
 * Create by bruno.abreu.prestador on April/2020
 */

@Service
@Transactional
public class BarramentoServiceImpl implements BarramentoService {

	@Value("${prf.barramento.url}")
    private String url;
	
	
	@Override
	public Optional<AutoInfracao> consultaAutoInfracao(String autoInfracao) {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
				.path("/multas/" + autoInfracao);
		
        RestTemplate restTemplate = new RestTemplate();
		return Optional.ofNullable(restTemplate.getForObject(builder.toUriString(), AutoInfracao.class));
	}

}
