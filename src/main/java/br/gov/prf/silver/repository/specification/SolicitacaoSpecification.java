package br.gov.prf.silver.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.domain.Recolhimento_;
import br.gov.prf.silver.domain.Veiculo;
import br.gov.prf.silver.domain.Veiculo_;
import br.gov.prf.silver.domain.recolhimento.PolicialRecolhimento;
import br.gov.prf.silver.domain.recolhimento.PolicialRecolhimento_;
import br.gov.prf.silver.domain.recolhimento.SituacaoSolicitacao;
import br.gov.prf.silver.domain.recolhimento.SituacaoSolicitacao_;
import br.gov.prf.silver.domain.recolhimento.SolicitacaoCancelamento;
import br.gov.prf.silver.domain.recolhimento.SolicitacaoCancelamento_;
import br.gov.prf.silver.service.filtro.MonitorSolicitacaoFiltro;

/**
 * Created by bruno.abreu on November, 2019
 */
public final class SolicitacaoSpecification extends GenericSpecification {

    private SolicitacaoSpecification() {

    }

    public static Specification<SituacaoSolicitacao> filtroPesquisa(MonitorSolicitacaoFiltro filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<SituacaoSolicitacao, Recolhimento> srJoin = root.join(SituacaoSolicitacao_.recolhimento);
            Join<Recolhimento, Veiculo> rvJoin = srJoin.join(Recolhimento_.veiculo);
            
            if(filtro.getNumeroRecolhimento() != null && filtro.getNumeroRecolhimento() != "") {
            	
            	predicates.add(criteriaBuilder.equal(srJoin.get(Recolhimento_.drv), filtro.getNumeroRecolhimento()));
            }
            if(filtro.getPlaca() != null && filtro.getPlaca() != "") {
            	
            	predicates.add(criteriaBuilder.equal(rvJoin.get(Veiculo_.placa), filtro.getPlaca()));
            }
            if(filtro.getChassi() != null && filtro.getChassi() != "") {
            	
            	predicates.add(criteriaBuilder.equal(rvJoin.get(Veiculo_.chassi), filtro.getChassi()));
            }
            if(filtro.getRenavam() != null && filtro.getRenavam() != "") {
            	
            	predicates.add(criteriaBuilder.equal(rvJoin.get(Veiculo_.renavam), filtro.getRenavam()));
            }
            
            if(filtro.getPolicial() != null && filtro.getPolicial() != "") {

            	Join<Recolhimento, PolicialRecolhimento> pJoin = srJoin.join(Recolhimento_.prfResponsavel);
    			predicates.add(
    					criteriaBuilder.like(criteriaBuilder.lower(pJoin.get(PolicialRecolhimento_.nome)), 
    		        			getPatternLikePercentAfterAndBefore(filtro.getPolicial()).toLowerCase()));
            }
            
		    if(filtro.getDataInicial() != null || filtro.getDataFinal() != null){

				if(filtro.getDataInicial() != null && filtro.getDataFinal() == null) {
					
				    predicates.add(criteriaBuilder.greaterThanOrEqualTo(
				    		root.get(SituacaoSolicitacao_.dhInclusao), filtro.getDataInicial()));
				} else if (filtro.getDataInicial() == null && filtro.getDataFinal() != null) {
					
				    predicates.add(criteriaBuilder.lessThanOrEqualTo(
				    		root.get(SituacaoSolicitacao_.dhInclusao), filtro.getDataFinal()));
				} else {
					
				    predicates.add(criteriaBuilder.between(root.get(SituacaoSolicitacao_.dhInclusao), 
				    		filtro.getDataInicial().minusDays(1L), filtro.getDataFinal().plusDays(1L)));
				}
			}
		    
		    if(filtro.getDescricao() != null && filtro.getDescricao() != "") {

	            Join<SituacaoSolicitacao, SolicitacaoCancelamento> ssJoin = root.join(SituacaoSolicitacao_.solicitacao);
		    	predicates.add(
    					criteriaBuilder.like(criteriaBuilder.lower(ssJoin.get(SolicitacaoCancelamento_.descricaoSolicitacao)), 
    		        			getPatternLikePercentAfterAndBefore(filtro.getDescricao()).toLowerCase()));
            }
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}




