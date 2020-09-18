package br.gov.prf.silver.exceptions;

public class TokenException extends Exception {

    private static final long serialVersionUID = 1L;

    private Object[] params;

    public TokenException(String message, Object... params) {
        super(message);
        this.params = params;
    }

    Object[] getParams() {
        return params;
    }
}
