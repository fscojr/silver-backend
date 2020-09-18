package br.gov.prf.silver.service.impl.client;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import br.gov.prf.silver.domain.recolhimento.VeiculoAutoInfracao;
import br.gov.prf.silver.service.MotorConsultaService;

/**
 * Create by bruno.abreu.prestador on April/2020
 */

@Service
@Transactional
public class MotorConsultaServiceImpl implements MotorConsultaService {

	@Value("${prf.motor-consulta.url}")
    private String url;

	@Value("${prf.matricula}")
    private String matricula;

	@Value("${prf.cpf}")
    private String cpf;
	
	
	@Override
	public Optional<VeiculoAutoInfracao> consultaVeiculo(String chave, String campo) {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
				.queryParam("matricula", this.matricula)
	            .queryParam("cpf", this.cpf)
	            .queryParam("objeto", "VEICULO")
	            .queryParam("campo", campo)
	            .queryParam("chave", chave)
	            .queryParam("sistemas", "21,22,23");
		
        RestTemplate restTemplate = new RestTemplate();
		return Optional.ofNullable(restTemplate.getForObject(builder.toUriString(), VeiculoAutoInfracao.class));
	}
	
}
