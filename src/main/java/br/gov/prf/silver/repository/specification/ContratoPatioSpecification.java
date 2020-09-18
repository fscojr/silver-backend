package br.gov.prf.silver.repository.specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.gov.prf.silver.domain.ContratoPatio;
import br.gov.prf.silver.domain.ContratoPatio_;
import br.gov.prf.silver.domain.contratoPatio.Contratada;
import br.gov.prf.silver.domain.contratoPatio.Contratada_;
import br.gov.prf.silver.domain.contratoPatio.ServicoContrato;
import br.gov.prf.silver.domain.contratoPatio.ServicoContrato_;
import br.gov.prf.silver.service.filtro.ContratoPatioFiltro;

/**
 * Created by bruno.abreu on November, 2019
 */
public final class ContratoPatioSpecification extends GenericSpecification {

    private ContratoPatioSpecification() {

    }

    public static Specification<ContratoPatio> filtroPesquisa(ContratoPatioFiltro filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if(filtro.getProcessoSei() != null && !filtro.getProcessoSei().equals("")){
            	predicates.add(criteriaBuilder.equal(root.get(ContratoPatio_.processoSei), filtro.getProcessoSei()));
            }
            if(filtro.getNumeroContrato() != null && !filtro.getNumeroContrato().equals("")){
            	predicates.add(criteriaBuilder.equal(root.get(ContratoPatio_.numeroContrato), filtro.getNumeroContrato()));
            }
            if(filtro.getContratada() != null && !filtro.getContratada().equals("")){
            	Join<ContratoPatio, Contratada> contratadaJoin = root.join(ContratoPatio_.contratada);
            	predicates.add(criteriaBuilder.like(criteriaBuilder.lower(contratadaJoin.get(Contratada_.nome)), 
            			getPatternLikePercentAfterAndBefore(filtro.getContratada()).toLowerCase()));
            }
            if(filtro.getRegional() != null){
            	predicates.add(criteriaBuilder.equal(root.get(ContratoPatio_.regional), filtro.getRegional()));
            }
            if(filtro.getUnidade() != null){
            	predicates.add(criteriaBuilder.equal(root.get(ContratoPatio_.unidade), filtro.getUnidade()));
            }
            if(filtro.getPatio() != null){
            	predicates.add(criteriaBuilder.equal(root.get(ContratoPatio_.patio), filtro.getPatio()));
            }
            if(filtro.getVigencia() != null){
            	verificaVigencia(filtro, root, criteriaBuilder, predicates);
            }
            if(filtro.getServico() != null && !filtro.getServico().isEmpty()){
            	Join<ContratoPatio, ServicoContrato> servicoJoin = root.join(ContratoPatio_.servicoContratroList);
            	predicates.add(servicoJoin.get(ServicoContrato_.tipoServico).in(filtro.getServico()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }

	private static void verificaVigencia(ContratoPatioFiltro filtro, Root<ContratoPatio> root,
			CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {

    	LocalDate dataAtual = LocalDate.now();
		if(filtro.getVigencia()){
			predicates.add(criteriaBuilder.or(
	    		criteriaBuilder.greaterThan(root.get(ContratoPatio_.dtInicioVigencia), dataAtual),
				criteriaBuilder.lessThan(root.get(ContratoPatio_.dtFimVigencia), dataAtual))
    		);		    
		} else {
			predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(ContratoPatio_.dtInicioVigencia), dataAtual));
		    predicates.add(criteriaBuilder.or(
				criteriaBuilder.greaterThanOrEqualTo(root.get(ContratoPatio_.dtFimVigencia), dataAtual),
				criteriaBuilder.isNull(root.get(ContratoPatio_.dtFimVigencia)))
			);
		}
	}
}




