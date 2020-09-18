package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.beans.RequestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseController {

    /**
     * The Logger for this Class.
     */
    protected static final Logger logger = LoggerFactory.getLogger(BaseController.class);
    private RequestResponse response;

    public BaseController() {
        this.response = new RequestResponse();
        this.response.setStatus("success");
    }

    protected RequestResponse getResponse() {
        return this.response;
    }

    protected BaseController setResponse(Object model) {
        this.response.setData(model);
        return this;
    }

}
