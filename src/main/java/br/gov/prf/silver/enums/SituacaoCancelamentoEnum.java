package br.gov.prf.silver.enums;

public enum SituacaoCancelamentoEnum {

    AGUARDANDO_ATENDIMENTO(1L, "Aguardando atendimento"),
    DEFERIDO(2L, "Deferido"),
    INDEFERIDO(3L, "Indeferido"),
    CANCELAMENTO(4L, "Cancelado");

    private Long chave;
    private String descricao;

    SituacaoCancelamentoEnum(Long chave, String descricao) {
        this.chave = chave;
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return chave;
    }

    public String getDescricao() {
        return descricao;
    }

    public static SituacaoCancelamentoEnum getPeloCodigo(Long codigo){
        for (SituacaoCancelamentoEnum valor : values()) {
            if(valor.getCodigo().equals(codigo)){
                return valor;
            }
        }
        return null;
    }
}
