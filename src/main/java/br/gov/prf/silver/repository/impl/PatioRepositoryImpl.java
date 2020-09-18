package br.gov.prf.silver.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.prf.silver.domain.Patio;
import br.gov.prf.silver.repository.custom.PatioRepositoryCustom;
import br.gov.prf.silver.service.filtro.PatioFiltro;

/**
 * Create by bruno.abreu.prestador on January/2020
 */

@Repository
@Transactional(readOnly = true)
public class PatioRepositoryImpl implements PatioRepositoryCustom {

	@PersistenceContext
    private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Patio> obterListaPatioPorParametro(PatioFiltro filtro) {
		StringBuilder sql = this.sqlListaPatioPorParametro(filtro);
        Query query = entityManager.createQuery(sql.toString(), Patio.class);
        if (filtro != null) {
            query =  this.validaValoresParametro(filtro, query);
        }
        return query.getResultList();
    }

    private StringBuilder sqlListaPatioPorParametro(PatioFiltro filtro) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT p FROM Patio p  WHERE 1=1 ");
        if (filtro != null) {
            if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
                sql.append(" AND LOWER(p.nome) LIKE :nome ");
            }
            if (filtro.getTipoPatio() != null) {
                sql.append(" AND p.tipoPatio = :tipoPatio ");
            }
            if (filtro.getRegional() != null){
                sql.append(" AND p.regional = :regional ");
            }
            if (filtro.getUnidade() != null){
                sql.append(" AND p.unidade = :unidade ");
            }
            if (filtro.getUf() != null){
                sql.append(" AND p.uf = :uf ");
            }
            if (filtro.getMunicipio() != null){
                sql.append(" AND p.municipio = :municipio ");
            }
            if (filtro.getAtivo() != null){
                sql.append(" AND p.ativo = :ativo ");
            }
            if (filtro.getNomeResponsavel() != null && !filtro.getNomeResponsavel().isEmpty()){
                sql.append(" AND LOWER(p.nomeResponsavel) LIKE :nomeResponsavel ");
            }
            sql.append(" ORDER BY mr.motivo ");
        }
        return sql;
    }

    private Query validaValoresParametro(PatioFiltro filtro, Query query) {
        if (filtro.getNome() != null && !filtro.getNome().isEmpty()) {
            query.setParameter("nome", "%" + filtro.getNome().toLowerCase() + "%");
        }
        if (filtro.getTipoPatio() != null) {
            query.setParameter("tipoPatio", filtro.getTipoPatio());
        }
        if (filtro.getRegional() != null){
            query.setParameter("regional", filtro.getRegional());
        }
        if (filtro.getUnidade() != null){
            query.setParameter("unidade", filtro.getUnidade());
        }
        if (filtro.getUf() != null){
            query.setParameter("uf", filtro.getUf());
        }
        if (filtro.getMunicipio() != null){
            query.setParameter("municipio", filtro.getMunicipio());
        }
        if (filtro.getAtivo() != null) {
            query.setParameter("ativo", filtro.getAtivo());
        }
        if (filtro.getNomeResponsavel() != null && !filtro.getNomeResponsavel().isEmpty()) {
            query.setParameter("nomeResponsavel", "%" + filtro.getNomeResponsavel().toLowerCase() + "%");
        }
        return query;
    }

}