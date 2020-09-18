package br.gov.prf.silver.repository.impl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.prf.silver.domain.ContratoPatio;
import br.gov.prf.silver.repository.custom.ContratoPatioRepositoryCustom;
import br.gov.prf.silver.service.filtro.ContratoPatioFiltro;

/**
 * Create by bruno.abreu.prestador on January/2020
 */

@Repository
@Transactional(readOnly = true)
public class ContratoPatioRepositoryImpl implements ContratoPatioRepositoryCustom {

	@PersistenceContext
    private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<ContratoPatio> obterListaContratoPatioPorParametro(ContratoPatioFiltro filtro) {
		StringBuilder sql = this.sqlListaContratoPatioPorParametro(filtro);
        Query query = entityManager.createQuery(sql.toString(), ContratoPatio.class);
        if (filtro != null) {
            query =  this.validaValoresParametro(filtro, query);
        }
        return query.getResultList();
    }

    private StringBuilder sqlListaContratoPatioPorParametro(ContratoPatioFiltro filtro) {
        StringBuilder sql = new StringBuilder();
        sql.append("     SELECT cp FROM ContratoPatio cp "
        		 + " INNER JOIN Contratada cc ON cc.id = cp.contratada.id "
        		 + " LEFT JOIN ServicoContrato sc ON sc.contratoPatio.id = cp.id "
        		 + "      WHERE 1=1 ");
        if (filtro != null) {
            if (filtro.getProcessoSei() != null && !filtro.getProcessoSei().equals("")) {
                sql.append(" AND cp.processoSei = :processoSei ");
            }
            if (filtro.getNumeroContrato() != null && !filtro.getNumeroContrato().equals("")) {
                sql.append(" AND cp.numeroContrato = :numeroContrato ");
            }
            if (filtro.getContratada() != null && !filtro.getContratada().equals("")) {
                sql.append(" AND LOWER(cc.nome) LIKE :contratada ");
            }
            if (filtro.getRegional() != null) {
                sql.append(" AND cp.regional = :regional ");
            }
            if (filtro.getUnidade() != null) {
                sql.append(" AND cp.unidade = :unidade ");
            }
            if (filtro.getPatio() != null) {
                sql.append(" AND cp.patio = :patio ");
            }
            if (filtro.getVigencia() != null) {
        		if(filtro.getVigencia()){
        			sql.append(" AND (cp.dtInicioVigencia > :dataAtual OR cp.dtFimVigencia < :dataAtual)");
        		} else {
        			sql.append(" AND (cp.dtInicioVigencia <= :dataAtual "
        					+ "  AND (cp.dtFimVigencia >= :dataAtual OR cp.dtFimVigencia is null))");
        		}
                
            }
            if (!filtro.getServico().isEmpty()) {
                sql.append(" AND sc.tipoServico.id = :servico ");
            }
            
            sql.append(" ORDER BY cp.regional ");
        }
        return sql;
    }

    private Query validaValoresParametro(ContratoPatioFiltro filtro, Query query) {
        if (filtro.getProcessoSei() != null && !filtro.getProcessoSei().equals("")) {
            query.setParameter("processoSei", filtro.getProcessoSei());
        }
        if (filtro.getNumeroContrato() != null && !filtro.getNumeroContrato().equals("")) {
            query.setParameter("numeroContrato", filtro.getNumeroContrato());
        }
        if (filtro.getContratada() != null && !filtro.getContratada().equals("")) {
            query.setParameter("contratada", "%" + filtro.getContratada().toLowerCase() + "%");
        }
        if (filtro.getRegional() != null) {
            query.setParameter("regional", filtro.getRegional());
        }
        if (filtro.getUnidade() != null) {
            query.setParameter("unidade", filtro.getUnidade());
        }
        if (filtro.getPatio() != null) {
            query.setParameter("patio", filtro.getPatio());
        }
        if (filtro.getVigencia() != null) {
        	LocalDate dataAtual = LocalDate.now();
            query.setParameter("dataAtual", dataAtual);
        }
        if (!filtro.getServico().isEmpty()) {
            query.setParameter("servico", filtro.getServico());
        }
        return query;
    }

}