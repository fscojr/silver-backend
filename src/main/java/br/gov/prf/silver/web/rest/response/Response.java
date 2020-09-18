package br.gov.prf.silver.web.rest.response;

import java.util.HashMap;
import java.util.Map;

public class Response<T> {

    private T result;
    private Map<String, String> errors;

    public Response() {
    }

    public Response(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T object) {
        this.result = object;
    }

    public Map<String, String> getErrors() {
        if (errors == null) {
            errors = new HashMap<>();
        }
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

}
