package br.gov.prf.silver.exceptions;

public class BusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    private Object[] params;

    public BusinessException(String message, Object... params) {
        super(message);
        this.params = params;
    }

    Object[] getParams() {
        return params;
    }
}
