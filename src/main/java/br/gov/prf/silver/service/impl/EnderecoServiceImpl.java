package br.gov.prf.silver.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.prf.silver.domain.EnderecoPatio;
import br.gov.prf.silver.domain.EnderecoPatioHst;
import br.gov.prf.silver.repository.EnderecoPatioHstRepository;
import br.gov.prf.silver.repository.EnderecoPatioRepository;
import br.gov.prf.silver.repository.PaisRepository;
import br.gov.prf.silver.service.EnderecoService;
import br.gov.prf.silver.service.dto.EnderecoPatioDTO;
import br.gov.prf.silver.service.dto.EnderecoPatioHstDTO;
import br.gov.prf.silver.service.dto.MunicipioDTO;
import br.gov.prf.silver.service.dto.PaisDTO;
import br.gov.prf.silver.service.dto.UfDTO;
import br.gov.prf.silver.service.mapper.EnderecoPatioHstMapper;
import br.gov.prf.silver.service.mapper.EnderecoPatioMapper;
import br.gov.prf.silver.service.mapper.PaisMapper;
import br.gov.prf.silver.util.CorreiosClient;
import br.gov.prf.wsclient.servo2.WSServo2;
import br.gov.prf.wsclient.servo2.consulta.ConsultaMunicipio;
import br.gov.prf.wsclient.servo2.dominio.Municipio;
import br.gov.prf.wsclient.servo2.dominio.Uf;

/**
 * Review by bruno.abreu.prestador on November/2019
 */

@Service
@Transactional
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired private EnderecoPatioRepository enderecoPatioRepository;
    @Autowired private EnderecoPatioHstRepository enderecoPatioHstRepository;
    @Autowired private PaisRepository paisRepository;
    @Autowired private EnderecoPatioMapper enderecoPatioMapper;
    @Autowired private EnderecoPatioHstMapper enderecoPatioHstMapper;
    @Autowired private PaisMapper paisMapper;


    @Override
    public List<Uf> buscarTodasUfs() {
        return WSServo2.getWsUf().listar();
    }

    @Override
    public Uf buscarUf(String uf) {
        return WSServo2.getWsUf().buscarSigla(uf);
    }

    @Override
    public List<MunicipioDTO> buscarMunicipiosPelaUfs(String uf) throws InterruptedException {
        List<MunicipioDTO> municipioList = new ArrayList<>();
        List<Municipio> municipios = new ConsultaMunicipio().setUf(uf).getTudo();

        for (Municipio municipio : municipios) {
            MunicipioDTO dto = new MunicipioDTO();
            dto.setId(municipio.getId());
            dto.setNome(municipio.getNome());
            municipioList.add(dto);
        }
        return municipioList;
    }

    @Override
    public EnderecoPatio buscarEnderecoPeloCep(String cep) throws IOException, InterruptedException {
        return CorreiosClient.buscaEnderecoByCEP(cep);
    }

    @Override
    public EnderecoPatioDTO salvarEnderecoPatio(EnderecoPatioDTO dto) {
        EnderecoPatio enderecoPatio = this.enderecoPatioMapper.toEntity(dto);
        dataHoraAcao(dto, enderecoPatio);
        enderecoPatio.setCep(removeTraco(dto));
        this.enderecoPatioRepository.save(enderecoPatio);
        dto.setId(enderecoPatio.getId());
        salvarHistorico(dto);
        return dto;
    }

	private void salvarHistorico(EnderecoPatioDTO dto) {
		EnderecoPatioHstDTO dtoHst = new EnderecoPatioHstDTO();
		BeanUtils.copyProperties(dto, dtoHst);
        EnderecoPatioHst hst = this.enderecoPatioHstMapper.toEntity(dto);
		recuperarEnderecoPatioPersistente(dto.getId(), hst);
		hst.setCep(removeTraco(dto));
		hst.setDhInclusao(LocalDateTime.now());
		hst.setCpfResponsavel("00000000000");//TODO: ajustar para o cpf de quem esta logado
		this.enderecoPatioHstRepository.save(hst);
	}

    @Override
    public List<PaisDTO> listaPaises() {
        return paisMapper.toDto(paisRepository.findAll());
    }

    @Override
    public List<MunicipioDTO> buscarMunicipioNome(String nome) throws InterruptedException {
    	
		List<MunicipioDTO> municipioList = new ArrayList<>();
    	List<Municipio> municipios = new ConsultaMunicipio().setNome(nome).getTudo();
		
		for(Municipio municipio : municipios) {
			MunicipioDTO dto = new MunicipioDTO();
			dto.setId(municipio.getId());
			dto.setNome(municipio.getNome());
			dto.setIbgeAntigo(municipio.getIbgeAntigo());
			ajustaUF(municipio, dto);
			municipioList.add(dto);
		}
    	
		return municipioList;
    }

	private void ajustaUF(Municipio municipio, MunicipioDTO dto) {
		UfDTO uf = new UfDTO();
		uf.setId(municipio.getUf().getId());
		uf.setNome(municipio.getUf().getNome());
		uf.setSigla(municipio.getUf().getSigla());
		dto.setUf(uf);
	}
    
	@Override
    public MunicipioDTO buscarMunicipioCodigoIbge(String byIbgeAntigo) {
		Municipio municipioServo = WSServo2.getWSMunicipio().byIbgeAntigo(byIbgeAntigo);
		MunicipioDTO municipio = new MunicipioDTO();
		municipio.setId(municipioServo.getId());
		municipio.setNome(municipioServo.getNome());
		municipio.setIbgeAntigo(municipioServo.getIbgeAntigo());
        return municipio;
    }

    private void dataHoraAcao(EnderecoPatioDTO dto, EnderecoPatio enderecoPatio) {
        if (dto.getId() == null) {
            enderecoPatio.setDhInclusao(LocalDateTime.now());
        } else {
            enderecoPatio.setDhAtualizacao(LocalDateTime.now());
        }
    }

    private String removeTraco(EnderecoPatioDTO dto) {
        return dto.getCep().replace("-", "");
    }
    
    private void recuperarEnderecoPatioPersistente(Long id, 
			EnderecoPatioHst hst) {
		Optional<EnderecoPatio> optional = this.enderecoPatioRepository.findById(id);
		optional.ifPresent(hst::setEnderecoPatio);
	}

}
