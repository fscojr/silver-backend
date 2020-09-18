package br.gov.prf.silver.enums;

public enum TrueFalseEnum {

    TRUE("t", "true"),
    FALSE("f", "false");

    private String chave;
    private String descricao;

    TrueFalseEnum(String chave, String descricao) {
        this.chave = chave;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return chave;
    }

    public String getDescricao() {
        return descricao;
    }

    public boolean isInativo() {
        return FALSE == this;
    }

    public static TrueFalseEnum getPeloCodigo(String codigo){
        for (TrueFalseEnum valor : values()) {
            if(valor.getCodigo().equals(codigo)){
                return valor;
            }
        }
        return null;
    }
}
