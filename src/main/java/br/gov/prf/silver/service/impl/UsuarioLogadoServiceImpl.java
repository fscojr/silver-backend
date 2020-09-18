package br.gov.prf.silver.service.impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import br.gov.dprf.seguranca.dominio.UsuarioLogin;
import br.gov.prf.silver.service.UsuarioLogadoService;

@Service
public class UsuarioLogadoServiceImpl implements UsuarioLogadoService {

    @Override
    public UsuarioLogin recuperaUsuarioLogado() {
    	if(SecurityContextHolder.getContext().getAuthentication().getPrincipal().equals("anonymousUser")) {
			return null;
		} else {
			return (UsuarioLogin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
    }

}
