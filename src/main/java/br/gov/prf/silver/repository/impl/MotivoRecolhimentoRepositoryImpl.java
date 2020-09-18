package br.gov.prf.silver.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.prf.silver.domain.MotivoRecolhimento;
import br.gov.prf.silver.repository.custom.MotivoRecolhimentoRepositoryCustom;
import br.gov.prf.silver.service.filtro.MotivoRecolhimentoFiltro;

/**
 * Create by bruno.abreu.prestador on January/2020
 */

@Repository
@Transactional(readOnly = true)
public class MotivoRecolhimentoRepositoryImpl implements MotivoRecolhimentoRepositoryCustom {

	@PersistenceContext
    private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<MotivoRecolhimento> obterListaMotivoRecolhimentoPorParametro(MotivoRecolhimentoFiltro filtro) {
		StringBuilder sql = this.sqlListaMotivoRecolhimentoPorParametro(filtro);
        Query query = entityManager.createQuery(sql.toString(), MotivoRecolhimento.class);
        if (filtro != null) {
            query =  this.validaValoresParametro(filtro, query);
        }
        return query.getResultList();
    }

    private StringBuilder sqlListaMotivoRecolhimentoPorParametro(MotivoRecolhimentoFiltro filtro) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT mr FROM MotivoRecolhimento mr  WHERE 1=1 ");
        if (filtro != null) {
            if (filtro.getMotivo() != null) {
                sql.append(" AND mr.motivo = :motivo ");
            }
            if (filtro.getOutroMotivo() != null && !filtro.getOutroMotivo().isEmpty()) {
                sql.append(" AND mr.outroMotivo = :outroMotivo ");
            }
            if (filtro.getAmparoLegal() != null && !filtro.getAmparoLegal().isEmpty()){
                sql.append(" AND LOWER(mr.amparoLegal) LIKE :amparoLegal ");
            }
            if (filtro.getTipoDocumento() != null && !filtro.getTipoDocumento().isEmpty()){
                sql.append(" AND mr.tipoDocumento = :tipoDocumento ");
            }
            if (filtro.getAtivo() != null){
                sql.append(" AND mr.ativo = :ativo ");
            }
            sql.append(" ORDER BY mr.motivo ");
        }
        return sql;
    }

    private Query validaValoresParametro(MotivoRecolhimentoFiltro filtro, Query query) {
        if (filtro.getMotivo() != null) {
            query.setParameter("motivo", filtro.getMotivoDTO());
        }
        if (filtro.getOutroMotivo() != null && !filtro.getOutroMotivo().isEmpty()) {
            query.setParameter("outroMotivo", filtro.getOutroMotivo());
        }
        if (filtro.getAmparoLegal() != null && !filtro.getAmparoLegal().isEmpty()) {
            query.setParameter("amparoLegal", "%" + filtro.getAmparoLegal().toLowerCase() + "%");
        }
        if (filtro.getTipoDocumento() != null && !filtro.getTipoDocumento().isEmpty()) {
            query.setParameter("tipoDocumento", filtro.getTipoDocumento());
        }
        if (filtro.getAtivo() != null) {
            query.setParameter("ativo", filtro.getAtivo());
        }
        return query;
    }

}