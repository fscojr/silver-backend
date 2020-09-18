package br.gov.prf.silver.util;

import java.text.Normalizer;

public class StringUtils {

    public static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

}
