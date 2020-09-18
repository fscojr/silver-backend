package br.gov.prf.silver.web.rest;

import br.gov.dprf.seguranca.dominio.FormularioLogin;
import br.gov.prf.silver.security.jwt.JwtProvider;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "Auth", description = "Lista de operações para o recurso auth")
public class AuthController extends BaseController {

    private JwtProvider jwtProvider;

    @Autowired
    public AuthController(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Realiza o login no DPRF Segurança")
    public ResponseEntity login(@RequestBody FormularioLogin login) {
        final Map<String, Object> model = new HashMap<>();
        model.put("cpf", login.getChave());
        model.put("token", jwtProvider.authenticate(login));

        return ResponseEntity.ok(model);
    }

}
