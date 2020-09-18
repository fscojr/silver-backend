package br.gov.prf.silver.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.gov.prf.silver.domain.EnderecoPatio;
import br.gov.prf.silver.domain.EnderecoPatio_;
import br.gov.prf.silver.domain.Patio;
import br.gov.prf.silver.domain.Patio_;
import br.gov.prf.silver.domain.ResponsavelPatio;
import br.gov.prf.silver.domain.ResponsavelPatio_;
import br.gov.prf.silver.domain.TaPatioUnidade;
import br.gov.prf.silver.domain.TaPatioUnidade_;
import br.gov.prf.silver.service.filtro.PatioFiltro;

/**
 * Created by bruno.abreu on November, 2019
 */
public final class PatioSpecification extends GenericSpecification {

    private PatioSpecification() {

    }

    public static Specification<Patio> filtroPesquisa(PatioFiltro filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(filtro.getRegional() != null || filtro.getUnidade() != null) {
	            Join<Patio, TaPatioUnidade> unidadeJoin = root.join(Patio_.patioUnidadeList);
	            if(filtro.getUnidade() != null){
	            	predicates.add(criteriaBuilder.equal(unidadeJoin.get(TaPatioUnidade_.unidade), filtro.getUnidade()));
	            } else if(filtro.getRegional() != null){
	            	predicates.add(criteriaBuilder.equal(unidadeJoin.get(TaPatioUnidade_.regional), filtro.getRegional()));
	            }
            }
            if(filtro.getNome() != null && !filtro.getNome().equals("")){
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(Patio_.nome)),
                        getPatternLikePercentAfterAndBefore(filtro.getNome().trim()).toLowerCase()));
            }
            if(filtro.getTipoPatio() != null){
            	predicates.add(criteriaBuilder.equal(root.get(Patio_.tipoPatio), filtro.getTipoPatio()));
            }
            if(filtro.getAtivo() != null){
            	predicates.add(criteriaBuilder.equal(root.get(Patio_.ativo), filtro.getAtivo()));
            }
            if(filtro.getMunicipio() != null){
            	Join<Patio, EnderecoPatio> enderecoJoin = root.join(Patio_.enderecoPatio);
            	predicates.add(criteriaBuilder.equal(enderecoJoin.get(EnderecoPatio_.municipio), 
            			filtro.getMunicipio()));
            } else if(filtro.getUf() != null){
            	Join<Patio, EnderecoPatio> enderecoJoin = root.join(Patio_.enderecoPatio);
            	predicates.add(criteriaBuilder.equal(enderecoJoin.get(EnderecoPatio_.ufSigla), 
            			filtro.getUf().toUpperCase()));
            }
            if(filtro.getNomeResponsavel() != null && !filtro.getNomeResponsavel().equals("")){
            	Join<Patio, ResponsavelPatio> responsavelJoin = root.join(Patio_.responsavelPatioList);
            	predicates.add(criteriaBuilder.like(
            			criteriaBuilder.lower(responsavelJoin.get(ResponsavelPatio_.nome)), 
            			getPatternLikePercentAfterAndBefore(filtro.getNomeResponsavel()).toLowerCase()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}




