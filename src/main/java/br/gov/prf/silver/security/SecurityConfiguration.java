package br.gov.prf.silver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import br.gov.prf.silver.security.jwt.JwtConfiguration;
import br.gov.prf.silver.security.jwt.JwtProvider;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private JwtProvider jwtProvider;

    @Autowired
    public SecurityConfiguration(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
            .cors().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/ping").permitAll()
            .antMatchers("/categoria-contratuais/**").hasRole("USER")
            .antMatchers("/condutores/**").hasRole("USER")
            .antMatchers("/contratadas/**").hasRole("USER")
            .antMatchers("/contrato-patios/**").hasRole("USER")
            .antMatchers("/descricao-servicos/**").hasRole("USER")
            .antMatchers("/drvs/**").hasRole("USER")
            .antMatchers("/enderecos/**").hasRole("USER")
            .antMatchers("/imagem-veiculos/**").hasRole("USER")
            .antMatchers("/monitor-solicitacaos/**").hasRole("USER")
            .antMatchers("/motivos-recolhimentos/**").hasRole("USER")
            .antMatchers("/patios/**").hasRole("USER")
            .antMatchers("/pertences/**").hasRole("USER")
            .antMatchers("/policial-recolhimentos/**").hasRole("USER")
            .antMatchers("/recolhimentos/**").hasRole("USER")
            .antMatchers("/regionais/**").hasRole("USER")
            .antMatchers("/remocaos/**").hasRole("USER")
            .antMatchers("/solicitacao-cancelamentos/**").hasRole("USER")
            .antMatchers("/transferencia-patios/**").hasRole("USER")
            .antMatchers("/trechos/**").hasRole("USER")
            .antMatchers("/unidades/**").hasRole("USER")
            .antMatchers("/veiculos/**").hasRole("USER")
            .antMatchers(
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**",
                "/error/**",
                "/auth/login"
            ).permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(new JwtConfiguration(jwtProvider));
    }
}
