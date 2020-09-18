package br.gov.prf.silver.security.exceptions;

public class BusinessException extends RuntimeException {

    private int      code;
    private Object[] params;

    public BusinessException(String message, Object... params) {
        super(message);
        this.params = params;
        this.code = 0;
    }

    public BusinessException(String message, int code) {
        super(message);
        this.code = code;
    }

    Object[] getParams() {
        return params;
    }

    @Override
    public int hashCode() {
        return this.code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
