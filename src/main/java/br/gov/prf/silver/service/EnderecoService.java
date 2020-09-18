package br.gov.prf.silver.service;

import java.io.IOException;
import java.util.List;

import br.gov.prf.silver.domain.EnderecoPatio;
import br.gov.prf.silver.service.dto.EnderecoPatioDTO;
import br.gov.prf.silver.service.dto.MunicipioDTO;
import br.gov.prf.silver.service.dto.PaisDTO;
import br.gov.prf.wsclient.servo2.dominio.Uf;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

public interface EnderecoService {

	List<Uf> buscarTodasUfs();

	Uf buscarUf(String uf);
	
	List<MunicipioDTO> buscarMunicipiosPelaUfs(String uf) throws InterruptedException;
	
    EnderecoPatio buscarEnderecoPeloCep(String cep) throws IOException, InterruptedException;

    EnderecoPatioDTO salvarEnderecoPatio(EnderecoPatioDTO dto);

    List<MunicipioDTO> buscarMunicipioNome(String nome) throws InterruptedException ;
    
    MunicipioDTO buscarMunicipioCodigoIbge(String byIbgeAntigo);

    List<PaisDTO> listaPaises();
}
