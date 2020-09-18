package br.gov.prf.silver.enums;

public enum SentidoEnum {

	CRESCENTE(1L, "C"),
	DECRESCENTE(2L, "D");

    private Long chave;
    private String descricao;

    SentidoEnum(Long chave, String descricao) {
        this.chave = chave;
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return chave;
    }

    public String getDescricao() {
        return descricao;
    }

    public static SentidoEnum getPeloCodigo(String codigo){
        for (SentidoEnum valor : values()) {
            if(valor.getCodigo().equals(codigo)){
                return valor;
            }
        }
        return null;
    }
}
