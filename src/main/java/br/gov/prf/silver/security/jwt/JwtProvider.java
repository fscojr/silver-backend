package br.gov.prf.silver.security.jwt;

import br.gov.dprf.geral.DPRFSegurancaLoginException;
import br.gov.dprf.seguranca.dominio.FormularioLogin;
import br.gov.dprf.seguranca.dominio.UsuarioLogin;
import br.gov.dprf.wsclient.ConfigWeb;
import br.gov.prf.silver.security.exceptions.BusinessException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    @Value("${prf.seguranca.token.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${prf.seguranca.token.expire-length:3600000}")
    private long validityInMilliseconds = 3600000;

    @Value("${prf.seguranca.sigla-sistema}")
    private String siglaSistema;

    @Value("${prf.seguranca.ambiente}")
    private String ambiente;

    @Value("${prf.seguranca.ip}")
    private String ip;

    @Autowired
    private MessageSource messageSource;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private String createToken(UsuarioLogin usuarioLogin) throws JsonProcessingException {
        Claims claims = Jwts.claims();
        claims.put("nome", usuarioLogin.getNome());
        claims.put("matricula", usuarioLogin.getMatricula());
        claims.put("nomeGuerra", usuarioLogin.getNomeGuerra());
        claims.put("cpf", usuarioLogin.getCpf());
        claims.put("email", usuarioLogin.getEmail());

        final Set<String> roles = usuarioLogin.getControlePermissoes().getSiglasDasFuncionalidades();
        roles.add("ROLE_USER");
        claims.put("roles", roles);

        final long now = System.currentTimeMillis();

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + validityInMilliseconds))
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String authenticate(FormularioLogin formularioLogin) {
        ConfigWeb.setModo(ambiente);
        try {
            final UsuarioLogin usuarioLogin = UsuarioLogin
                .loginPost(formularioLogin.getChave(), formularioLogin.getSenha(), ip, siglaSistema, formularioLogin.getToken());

            log.info("Usuário de CPF [" + formularioLogin.getChave() + "] autenticado no DPRFSegurança");
            return createToken(usuarioLogin);
        } catch (DPRFSegurancaLoginException | IOException e) {
            throw new BusinessException(e.getMessage());

        }
    }

    public Authentication getAuthentication(String token) {
        return new UsernamePasswordAuthenticationToken(parseToken(token), "", getAuthorities(token));
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = this.resolveToken(request);
        return getAuthentication(token);
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Set<SimpleGrantedAuthority> getAuthorities(String token) {
        List<String> roles = (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            .getBody().get("roles");
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return new Date().before(claims.getBody().getExpiration());
        } catch (JwtException | IllegalArgumentException e) {
            throw new BusinessException(messageSource.getMessage("MSG005", null, LocaleContextHolder.getLocale()));

        }
    }
    
    public UsuarioLogin parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            UsuarioLogin usuario = new UsuarioLogin();
            usuario.setCpf((String) body.get("cpf"));
            usuario.setMatricula((String) body.get("matricula"));
            usuario.setNome((String) body.get("nome"));
            usuario.setNomeGuerra((String) body.get("nomeGuerra"));
            usuario.setEmail((String) body.get("email"));
            return usuario;

        } catch (JwtException | ClassCastException e) {
            return null;
        }
    }
}
