package br.gov.prf.silver.repository.specification;

/**
 * Created by bruno.abreu.prestador on November, 2019
 */
public class GenericSpecification {

    protected GenericSpecification() {

    }

    protected static String getPatternLikePercentAfterAndBefore(String parametro){
        return "%" + parametro + "%";
    }

}




