package br.gov.prf.silver.enums;

public enum SimNaoEnum {

    SIM("S", "Sim"),
    NAO("N", "Não");

    private String chave;
    private String descricao;

    SimNaoEnum(String chave, String descricao) {
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
        return NAO == this;
    }

    public static SimNaoEnum getPeloCodigo(String codigo){
        for (SimNaoEnum valor : values()) {
            if(valor.getCodigo().equals(codigo)){
                return valor;
            }
        }
        return null;
    }
}
