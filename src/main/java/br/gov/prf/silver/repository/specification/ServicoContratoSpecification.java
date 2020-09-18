package br.gov.prf.silver.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.gov.prf.silver.domain.contratoPatio.ServicoContrato;

/**
 * Created by bruno.abreu on November, 2019
 */
public final class ServicoContratoSpecification extends GenericSpecification {

    private ServicoContratoSpecification() {

    }

    public static Specification<ServicoContrato> filtrarTipoServico(List<Long> tipos) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            /*if(tipos != null){
            	Join<ServicoContrato, TipoServicoContrato> tipoServicoJoin = root.join(ServicoContrato_.tipoServico);
            	predicates.add(tipoServicoJoin.get(TipoServicoContrato_.id).in(tipos));
            }*/
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}




