package br.gov.prf.silver.repository.specification;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.gov.prf.silver.domain.Recolhimento;
import br.gov.prf.silver.domain.Recolhimento_;
import br.gov.prf.silver.domain.Veiculo;
import br.gov.prf.silver.domain.Veiculo_;
import br.gov.prf.silver.domain.recolhimento.CondutorVeiculo;
import br.gov.prf.silver.domain.recolhimento.CondutorVeiculo_;
import br.gov.prf.silver.domain.recolhimento.EstadoVeiculo;
import br.gov.prf.silver.domain.recolhimento.EstadoVeiculo_;
import br.gov.prf.silver.domain.recolhimento.LocalRecolhimento;
import br.gov.prf.silver.domain.recolhimento.LocalRecolhimento_;
import br.gov.prf.silver.domain.recolhimento.MotivoRecolhimentoAuto;
import br.gov.prf.silver.domain.recolhimento.MotivoRecolhimentoAuto_;
import br.gov.prf.silver.domain.recolhimento.PolicialRecolhimento;
import br.gov.prf.silver.domain.recolhimento.PolicialRecolhimento_;
import br.gov.prf.silver.service.filtro.RecolhimentoFiltro;

/**
 * Created by bruno.abreu on November, 2019
 */
public final class RecolhimentoSpecification extends GenericSpecification {

    private RecolhimentoSpecification() {

    }

    public static Specification<Recolhimento> filtroPesquisa(RecolhimentoFiltro filtro) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            
            filtroDadosRecolhimento(filtro, root, criteriaBuilder, predicates);
            filtroDadosVeiculo(filtro, root, criteriaBuilder, predicates);
            filtroDadosCondutor(filtro, root, criteriaBuilder, predicates);
            filtroDadosLocalidade(filtro, root, criteriaBuilder, predicates);
            filtroDadosMotivoAuto(filtro, root, criteriaBuilder, predicates);
            filtroDadosPeriodo(filtro, root, criteriaBuilder, predicates);
            filtroDadosResponsavel(filtro, root, criteriaBuilder, predicates);
            
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }

	private static void filtroDadosResponsavel(RecolhimentoFiltro filtro, Root<Recolhimento> root,
			CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
		if(filtro.getPrfResponsavel() != null && !filtro.getPrfResponsavel().equals("")){
			Join<Recolhimento, PolicialRecolhimento> policialJoin = root.join(Recolhimento_.prfResponsavel);
			predicates.add(
					criteriaBuilder.like(criteriaBuilder.lower(
		        			policialJoin.get(PolicialRecolhimento_.nome)), 
		        			getPatternLikePercentAfterAndBefore(filtro.getPrfResponsavel()).toLowerCase())
			);
		}
	}

	private static void filtroDadosMotivoAuto(RecolhimentoFiltro filtro, Root<Recolhimento> root,
			CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
		if(filtro.getAutoInfracao() != null && !filtro.getAutoInfracao().equals("")){
			Join<Recolhimento, MotivoRecolhimentoAuto> motivoJoin = root.join(Recolhimento_.motivoAutoList);
			predicates.add(criteriaBuilder.equal(
					motivoJoin.get(MotivoRecolhimentoAuto_.autoDeInfracao), filtro.getAutoInfracao()));
		}
	}

	private static void filtroDadosRecolhimento(RecolhimentoFiltro filtro, Root<Recolhimento> root,
			CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
		if(filtro.getNumeroRecolhimento() != null && !filtro.getNumeroRecolhimento().equals("")){
			predicates.add(criteriaBuilder.equal(root.get(Recolhimento_.drv), filtro.getNumeroRecolhimento()));
		}
		if(filtro.getObservacao() != null && !filtro.getObservacao().equals("")){

			Join<Recolhimento, Veiculo> veiculoJoin = root.join(Recolhimento_.veiculo);
			Join<Veiculo, EstadoVeiculo> estadoveiculoJoin = veiculoJoin.join(Veiculo_.estadoVeiculo);

			predicates.add(criteriaBuilder.or(
				criteriaBuilder.like(criteriaBuilder.lower(root.get(Recolhimento_.providenciasRestituicao)), 
						getPatternLikePercentAfterAndBefore(filtro.getObservacao()).toLowerCase()),
				criteriaBuilder.like(criteriaBuilder.lower(estadoveiculoJoin.get(EstadoVeiculo_.observacao)), 
						getPatternLikePercentAfterAndBefore(filtro.getObservacao()).toLowerCase()))
    		);	
		}
		if(filtro.getSituacao() != null && !filtro.getSituacao().isEmpty()){
		    predicates.add(root.get(Recolhimento_.situacao).in(filtro.getSituacao()));
		}
	}

	private static void filtroDadosPeriodo(RecolhimentoFiltro filtro, Root<Recolhimento> root,
			CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {
		if(filtro.getDataInicial() != null || filtro.getDataFinal() != null){
			if(filtro.getDataInicial() != null && filtro.getDataFinal() == null) {
			    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Recolhimento_.dataRecolhimento), 
			    		filtro.getDataInicial()));
			} else if (filtro.getDataInicial() == null && filtro.getDataFinal() != null) {
			    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Recolhimento_.dataRecolhimento), 
			    		filtro.getDataFinal()));
			} else {
			    predicates.add(criteriaBuilder.between(root.get(Recolhimento_.dataRecolhimento), 
			    		filtro.getDataInicial().minusDays(1L), filtro.getDataFinal().plusDays(1L)));
			}
		}
	}

	private static void filtroDadosLocalidade(RecolhimentoFiltro filtro, Root<Recolhimento> root,
			CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {

		Join<Recolhimento, LocalRecolhimento> localJoin = root.join(Recolhimento_.localRecolhimento);
		if(filtro.getRegional() != null){
			
			predicates.add(criteriaBuilder.equal(localJoin.get(LocalRecolhimento_.regional), filtro.getRegional()));
		}
		if(filtro.getUnidade() != null){
			
			predicates.add(criteriaBuilder.equal(localJoin.get(LocalRecolhimento_.unidade), filtro.getUnidade()));
		}
		if(filtro.getPatio() != null){

			predicates.add(criteriaBuilder.equal(localJoin.get(LocalRecolhimento_.patio), filtro.getPatio()));
		}
	}

	private static void filtroDadosCondutor(RecolhimentoFiltro filtro, Root<Recolhimento> root,
			CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {

		Join<Recolhimento, CondutorVeiculo> condutorJoin = root.join(Recolhimento_.condutorVeiculo);
		Join<Recolhimento, Veiculo> veiculoJoin = root.join(Recolhimento_.veiculo);
		
		if(filtro.getCpfCondutor() != null && !filtro.getCpfCondutor().equals("")){

			predicates.add(criteriaBuilder.or(
	    		criteriaBuilder.equal(condutorJoin.get(CondutorVeiculo_.cpf), filtro.getCpfCondutor()),
				criteriaBuilder.equal(veiculoJoin.get(Veiculo_.cpfCnpjProprietario), filtro.getCpfCondutor()))
    		);	
		}
		if(filtro.getNomeCondutor() != null && !filtro.getNomeCondutor().equals("")){
			
			predicates.add(criteriaBuilder.or(
				criteriaBuilder.like(criteriaBuilder.lower(condutorJoin.get(CondutorVeiculo_.nome)), 
					getPatternLikePercentAfterAndBefore(filtro.getNomeCondutor()).toLowerCase()),
				criteriaBuilder.like(criteriaBuilder.lower(veiculoJoin.get(Veiculo_.nomeProprietario)), 
					getPatternLikePercentAfterAndBefore(filtro.getNomeCondutor()).toLowerCase()))
    		);	
		}
	}

	private static void filtroDadosVeiculo(RecolhimentoFiltro filtro, Root<Recolhimento> root,
			CriteriaBuilder criteriaBuilder, List<Predicate> predicates) {

		Join<Recolhimento, Veiculo> veiculoJoin = root.join(Recolhimento_.veiculo);
		
		if(filtro.getPlaca() != null){
			predicates.add(criteriaBuilder.equal(veiculoJoin.get(Veiculo_.placa), filtro.getPlaca()));
		}
		if(filtro.getChassi() != null && !filtro.getChassi().equals("")){
			predicates.add(criteriaBuilder.equal(veiculoJoin.get(Veiculo_.chassi), filtro.getChassi()));
		}
		if(filtro.getRenavam() != null && !filtro.getRenavam().equals("")){
			predicates.add(criteriaBuilder.equal(veiculoJoin.get(Veiculo_.renavam), filtro.getRenavam()));
		}
	}
}




