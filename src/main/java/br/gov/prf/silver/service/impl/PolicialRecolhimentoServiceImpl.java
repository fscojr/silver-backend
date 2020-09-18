package br.gov.prf.silver.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.gov.dprf.seguranca.dominio.UsuarioLogin;
import br.gov.dprf.wsclient.rh.WSRH;
import br.gov.dprf.wsclient.rh.dominio.Usuario;
import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.domain.recolhimento.PolicialRecolhimento;
import br.gov.prf.silver.repository.PolicialRecolhimentoRepository;
import br.gov.prf.silver.repository.RecolhimentoRepository;
import br.gov.prf.silver.service.PolicialRecolhimentoService;
import br.gov.prf.silver.service.UsuarioLogadoService;
import br.gov.prf.silver.service.dto.PolicialRecolhimentoDTO;
import br.gov.prf.silver.service.mapper.PolicialRecolhimentoMapper;

/**
 * Review by bruno.abreu.abreu on November, 2019
 */

@Service
@Transactional
public class PolicialRecolhimentoServiceImpl implements PolicialRecolhimentoService {

    @Autowired private PolicialRecolhimentoRepository policialRecolhimentoRepository;
    @Autowired private RecolhimentoRepository recolhimentoRepository;
    @Autowired private PolicialRecolhimentoMapper policialRecolhimentoMapper;
    @Autowired private UsuarioLogadoService usuarioLogadoService;

    
    @Override
    public List<PolicialRecolhimentoDTO> salvarPolicialAuxilizar(List<PolicialRecolhimentoDTO> prfList, Recolhimento recolhimento) {
        List<PolicialRecolhimento> policialRecolhimentoCollection = new ArrayList<>();
        for (PolicialRecolhimentoDTO dto : prfList) {
        	PolicialRecolhimento prf = policialRecolhimentoMapper.toEntity(dto);
        	recuperarRecolhimentoPersistente(recolhimento.getId(), prf);
            dataAcao(prf);
            this.policialRecolhimentoRepository.save(prf);
            policialRecolhimentoCollection.add(prf);
        }
        return policialRecolhimentoMapper.toDto(policialRecolhimentoCollection);
	}
    
    @Override
    public PolicialRecolhimentoDTO salvarPolicialResponsavel(Recolhimento recolhimento) {
    	
    	PolicialRecolhimento responsavel = new PolicialRecolhimento();
    	this.recuperarRecolhimentoPersistente(recolhimento.getId(), responsavel);
    	responsavel.setNome(recuperaUsuario().getNome());
    	responsavel.setCpf(recuperaUsuario().getCpf());
    	dataAcao(responsavel);
    	responsavel.setTipo('R');
    	responsavel.setMatricula(recuperaUsuario().getMatricula());
        this.policialRecolhimentoRepository.save(responsavel);

        return this.policialRecolhimentoMapper.toDto(responsavel);
	}


    @Override
    public List<PolicialRecolhimentoDTO> consultarPorRecolhimentoId(Long id) {
        List<PolicialRecolhimento> policial = this.policialRecolhimentoRepository.findByRecolhimentoId(id);
        return policialRecolhimentoMapper.toDto(policial);
    }
    
    
    private UsuarioLogin recuperaUsuario() {
		UsuarioLogin usuario = usuarioLogadoService.recuperaUsuarioLogado();
		return usuario;
	}
    
    private void dataAcao(PolicialRecolhimento prf) {
		if(prf.getId() == null) {
			prf.setAtivo(true);
			prf.setDhInclusao(LocalDateTime.now());
		}
	}
	
	private void recuperarRecolhimentoPersistente(Long recolhimento, PolicialRecolhimento prf) {
		Optional<Recolhimento> optional = this.recolhimentoRepository.findById(recolhimento);
		optional.ifPresent(prf::setRecolhimento);
	}

    private Boolean isIntAndRegistration(String nomeMatricula) {
        if (nomeMatricula.length() == 7) {
            try {
                Integer.parseInt(nomeMatricula);
                return Boolean.TRUE;
            } catch (NumberFormatException e) {
                return Boolean.FALSE;
            }
        }
        return Boolean.FALSE;
    }

    private PolicialRecolhimento createObject(Optional<Usuario> usuario) {
        PolicialRecolhimento policial = new PolicialRecolhimento();
        policial.setCpf(usuario.get().getCpf());
        policial.setTipo('A');
        policial.setNome(usuario.get().getNomeCompleto());
        policial.setMatricula(usuario.get().getMatricula());

        return policial;
    }

    @Override
    public List<PolicialRecolhimentoDTO> consultarPolicialPelaMatriculaCpf(String nomeMatricula) {
        if (this.isIntAndRegistration(nomeMatricula)) {
            Optional<Usuario> user = Optional.ofNullable(WSRH.getInstance().getUsuarioPorMatricula(nomeMatricula));
            if (user.isPresent()) {
                return policialRecolhimentoMapper.toDto(Arrays.asList(this.createObject(user)));
            }
        }

        Optional<List<Usuario>> users = Optional.ofNullable(WSRH.getInstance().pesquisaUsuarioPorNome(nomeMatricula));
        List<PolicialRecolhimento> policiais = new ArrayList<>();
        if (users.isPresent() && !users.get().isEmpty()) {
            for (Usuario user : users.get()) {
                policiais.add(this.createObject(Optional.ofNullable(user)));
            }
        }
        return policialRecolhimentoMapper.toDto(policiais);
    }

}
