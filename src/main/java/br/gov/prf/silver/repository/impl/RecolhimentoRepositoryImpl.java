package br.gov.prf.silver.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.repository.custom.RecolhimentoRepositoryCustom;
import br.gov.prf.silver.service.filtro.RecolhimentoFiltro;

/**
 * Create by bruno.abreu.prestador on January/2020
 */

@Repository
@Transactional(readOnly = true)
public class RecolhimentoRepositoryImpl implements RecolhimentoRepositoryCustom {

	@PersistenceContext
    private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Recolhimento> obterListaRecolhimentoPorParametro(RecolhimentoFiltro filtro) {
		StringBuilder sql = this.sqlListaRecolhimentoPorParametro(filtro);
        Query query = entityManager.createQuery(sql.toString(), Recolhimento.class);
        if (filtro != null) {
            query =  this.validaValoresParametro(filtro, query);
        }
        return query.getResultList();
    }

    private StringBuilder sqlListaRecolhimentoPorParametro(RecolhimentoFiltro filtro) {
        StringBuilder sql = new StringBuilder();
        sql.append("     SELECT r FROM Recolhimento r "
        		 + " INNER JOIN Veiculo v ON v.id = r.veiculo.id "
        		 + " INNER JOIN CondutorVeiculo cv ON cv.id = r.condutorVeiculo.id "
        		 + " INNER JOIN LocalRecolhimento lr ON lr.id = r.localRecolhimento.id "
        		 + " INNER JOIN MotivoRecolhimentoAuto mra ON mra.recolhimento.id = r.id "
        		 + " INNER JOIN PolicialRecolhimento pr ON pr.recolhimento.id = r.id "
        		 + " INNER JOIN SituacaoRecolhimento sr ON sr.id = r.situacao.id "
        		 + "      WHERE 1=1 ");
        if (filtro != null) {
            if (filtro.getNumeroRecolhimento() != null && !filtro.getNumeroRecolhimento().equals("")) {
                sql.append(" AND r.drv = :drv ");
            }
            if (filtro.getPlaca() != null && !filtro.getPlaca().equals("")) {
                sql.append(" AND v.placa = :placa ");
            }
            if (filtro.getChassi() != null && !filtro.getChassi().equals("")) {
                sql.append(" AND v.chassi = :chassi ");
            }
            if (filtro.getRenavam() != null && !filtro.getRenavam().equals("")) {
                sql.append(" AND v.renavam = :renavam ");
            }
            if (filtro.getCpfCondutor() != null && !filtro.getCpfCondutor().equals("")) {
                sql.append(" AND cv.cpf = :cpf ");
            }
            if (filtro.getNomeCondutor() != null && !filtro.getNomeCondutor().isEmpty()) {
                sql.append(" AND LOWER(cv.nome.id) LIKE :nome ");
            }
            if (filtro.getRegional() != null) {
                sql.append(" AND lr.regional = :regional ");
            }
            if (filtro.getUnidade() != null) {
                sql.append(" AND lr.unidade = :unidade ");
            }
            if (filtro.getPatio() != null) {
                sql.append(" AND r.patio.id = :patio ");
            }
            if (filtro.getAutoInfracao() != null && !filtro.getAutoInfracao().isEmpty()) {
                sql.append(" AND mra.autoDeInfracao = :autoDeInfracao ");
            }
            if (filtro.getPrfResponsavel() != null && !filtro.getPrfResponsavel().isEmpty()) {
                sql.append(" AND (LOWER(pr.policial) LIKE :nomeMatricula OR pr.matricula = :nomeMatricula) ");
            }
            if (filtro.getObservacao() != null && !filtro.getObservacao().isEmpty()) {
                sql.append(" AND LOWER(r.providenciasRestituicao) LIKE :observacao ");
            }
            if (filtro.getSituacao() != null && !filtro.getSituacao().isEmpty()) {
            	sql.append(" AND sr.id in (:situacao) ");
            }
            
            sql.append(" ORDER BY lr.regional ");
        }
        return sql;
    }

    private Query validaValoresParametro(RecolhimentoFiltro filtro, Query query) {
        if (filtro.getNumeroRecolhimento() != null && !filtro.getNumeroRecolhimento().equals("")) {
            query.setParameter("drv", filtro.getNumeroRecolhimento());
        }
        if (filtro.getPlaca() != null && !filtro.getPlaca().equals("")) {
            query.setParameter("placa", filtro.getPlaca());
        }
        if (filtro.getChassi() != null && !filtro.getChassi().equals("")) {
            query.setParameter("chassi", filtro.getChassi());
        }
        if (filtro.getRenavam() != null && !filtro.getRenavam().equals("")) {
            query.setParameter("renavam", filtro.getRenavam());
        }
        if (filtro.getCpfCondutor() != null && !filtro.getCpfCondutor().equals("")) {
            query.setParameter("cpf", filtro.getCpfCondutor());
        }
        if (filtro.getNomeCondutor() != null && !filtro.getNomeCondutor().isEmpty()) {
            query.setParameter("nome", "%" + filtro.getNomeCondutor().toLowerCase() + "%");
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
        if (filtro.getAutoInfracao() != null && !filtro.getAutoInfracao().isEmpty()) {
            query.setParameter("AutoDeInfracao", filtro.getAutoInfracao().toLowerCase());
        }
        if (filtro.getPrfResponsavel() != null && !filtro.getPrfResponsavel().isEmpty()) {
            query.setParameter("policial", "%" + filtro.getPrfResponsavel().toLowerCase() + "%");
        }
        if (filtro.getObservacao() != null && !filtro.getObservacao().isEmpty()) {
            query.setParameter("observacao", "%" + filtro.getObservacao().toLowerCase() + "%");
        }
        if (filtro.getSituacao() != null && !filtro.getSituacao().isEmpty()) {
            query.setParameter("situacao", filtro.getSituacao());
        }
        
        return query;
    }

}