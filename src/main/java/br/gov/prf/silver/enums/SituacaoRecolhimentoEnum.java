package br.gov.prf.silver.enums;

public enum SituacaoRecolhimentoEnum {

    VEICULO_NO_PATIO(1L, "Veículo no Pátio (Recolhido)"),
    LIBERACAO_AUTORIZADA(2L, "Liberação Autorizada"),
    EM_PROCESSO_DE_LEILAO(3L, "Em Processo de Leilão"),
    LIBERACAO_BLOQUEADA(4L, "Liberação Bloqueada"),
    LIBERACAO_CONDICIONADA(7L, "Em Liberação Condicionada"),
    PRAZO_REAPRES_EXPIRADO(8L, "Prazo Reapres. Expirado"),
    CANCELADO(9L, "Cancelado"),
    LIBERADO(10L, "Liberado"),
    ENCAMINHADO_PATIO_CONVENIADO(11L, "Encaminhado para Pátio Conveniado"),
    LEILOADO_LIBERADO(12L, "Leiloado, Liberado");

    private Long chave;
    private String descricao;

    SituacaoRecolhimentoEnum(Long chave, String descricao) {
        this.chave = chave;
        this.descricao = descricao;
    }

    public Long getCodigo() {
        return chave;
    }

    public String getDescricao() {
        return descricao;
    }

    public static SituacaoRecolhimentoEnum getPeloCodigo(Long codigo){
        for (SituacaoRecolhimentoEnum valor : values()) {
            if(valor.getCodigo().equals(codigo)){
                return valor;
            }
        }
        return null;
    }
}
