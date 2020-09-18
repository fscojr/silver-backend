package br.gov.prf.silver.web.rest;

import br.gov.prf.silver.beans.RequestResponse;
import br.gov.prf.silver.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/", produces = "application/json")
public class RootController extends BaseController {

    @Autowired
    private HttpServletRequest context;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping(path = "/ping")
    public RequestResponse ping() {
        return this.setResponse("sucesso").getResponse();
    }

    @GetMapping(path = "/me")
    public RequestResponse me() {
        return this.setResponse(
            this.jwtProvider.getAuthentication(context)
        ).getResponse();
    }

}
