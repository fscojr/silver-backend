package br.gov.prf.silver.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.gov.prf.silver.domain.MotivoRecolhimento;
import br.gov.prf.silver.domain.MotivoRecolhimento_;
import br.gov.prf.silver.service.filtro.MotivoRecolhimentoFiltro;

/**
 * Created by bruno.abreu on November, 2019
 */
public final class MotivoRecolhimentoSpecification extends GenericSpecification {

    private MotivoRecolhimentoSpecification() {

    }

    public static Specification<MotivoRecolhimento> filtroPesquisa(MotivoRecolhimentoFiltro filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            if(filtro.getMotivo() != null && !filtro.getMotivo().equals(null)){
            	predicates.add(criteriaBuilder.equal(root.get(MotivoRecolhimento_.motivo), filtro.getMotivo()));
            }
            if(filtro.getOutroMotivo() != null && !filtro.getOutroMotivo().equals("")){
            	predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(MotivoRecolhimento_.outroMotivo)),
                        getPatternLikePercentAfterAndBefore(filtro.getOutroMotivo()).toLowerCase()));
            }
            if(filtro.getAmparoLegal() != null && !filtro.getAmparoLegal().equals("")){
            	predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(MotivoRecolhimento_.amparoLegal)),
                        getPatternLikePercentAfterAndBefore(filtro.getAmparoLegal()).toLowerCase()));
            }
            if(filtro.getTipoDocumento() != null && !filtro.getTipoDocumento().equals("")){
            	predicates.add(criteriaBuilder.equal(root.get(MotivoRecolhimento_.tipoDocumento), filtro.getTipoDocumento()));
            }
            if(filtro.getAtivo() != null){
            	predicates.add(criteriaBuilder.equal(root.get(MotivoRecolhimento_.ativo), filtro.getAtivo()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}




