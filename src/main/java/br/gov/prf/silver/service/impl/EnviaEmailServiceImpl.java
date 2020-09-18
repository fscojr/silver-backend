package br.gov.prf.silver.service.impl;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.prf.silver.service.EnviaEmailService;
import br.gov.prf.silver.service.dto.ConfiguracaoEnvioEmailDTO;

/**
 * Service Implementation.
 */
@Service
@Transactional
public class EnviaEmailServiceImpl implements EnviaEmailService {

    private final Logger log = LoggerFactory.getLogger(SituacaoSolicitacaoServiceImpl.class);

    @Value("${prf.e-mail.hostname}")
    private String hostname;

    @Value("${prf.e-mail.porta}")
    private Integer porta;
    
    @Value("${prf.e-mail.timeout}")
    private Integer timeout;

    @Value("${prf.e-mail.username}")
    private String username;

    @Value("${prf.e-mail.password}")
    private String password;

    @Value("${prf.e-mail.remetente}")
    private String remetente;
    
    
    @Override
    public void gerandoEmail(String destinatarios, String assunto, String conteudo) 
    		throws MessagingException {
    	
        ConfiguracaoEnvioEmailDTO configuracao = obterConfiguracaoServidor();
        Message message = new MimeMessage(geraSessaoParaEnvioJavaMail(propriedadesSeguranca(), configuracao));

        try {
            Address destinos = new InternetAddress(destinatarios);
            message.setFrom(new InternetAddress(configuracao.getRemetente()));
            message.setRecipient(Message.RecipientType.TO, destinos);
            message.setSubject(assunto);
            message.setContent(conteudo, "text/html; charset=UTF-8");
            message.saveChanges();
        }catch (MessagingException e) {
            log.error(e.getMessage(), e);
            throw (e);
        }

        this.enviaEmail(message);
    }

    @Override
    public void enviaEmail(Message mensagem)  throws MessagingException{
        try {
            Transport.send(mensagem);
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
            throw (e);
        }
    }
    
    private ConfiguracaoEnvioEmailDTO obterConfiguracaoServidor() {
    	
    	ConfiguracaoEnvioEmailDTO configuracao = new ConfiguracaoEnvioEmailDTO();
    	configuracao.setHostname(hostname);
    	configuracao.setPorta(porta);
    	configuracao.setIndAutenticacao(true);
    	configuracao.setIndTLS(false);
    	configuracao.setUser(username);
    	configuracao.setPassword(password);
    	configuracao.setRemetente(remetente);
    	configuracao.setTimeout(timeout);
    	
    	return configuracao;
    }
    
    private Properties propriedadesSeguranca() {
    	ConfiguracaoEnvioEmailDTO configuracao = obterConfiguracaoServidor();
        Properties properties = new Properties();
        properties.put("mail.smtp.host", configuracao.getHostname());
        properties.put("mail.smtp.socketFactory.port", configuracao.getPorta());
        properties.put("mail.smtp.auth", configuracao.getIndAutenticacao());
        properties.put("mail.smtp.port", configuracao.getPorta());
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.starttls.enable", configuracao.getIndTLS());
        properties.put("mail.smtp.timeout", configuracao.getTimeout());
        populaPropriedadesUsuarioSenha(configuracao, properties);
        return properties;
    }
    
    private void populaPropriedadesUsuarioSenha(ConfiguracaoEnvioEmailDTO configuracao, Properties properties) {
        if (configuracao.getUser() != null) {
            properties.put("mail.smtp.user",configuracao.getUser());
        }
        if (configuracao.getPassword() != null) {
            properties.put("mail.smtp.password", configuracao.getPassword());
        }
    }

    private Session geraSessaoParaEnvioJavaMail(Properties propriedades, ConfiguracaoEnvioEmailDTO configuracao) {
        if (configuracao.getIndAutenticacao() == true || configuracao.getIndTLS() == true && configuracao.getPassword() != null){
            Session session = Session.getInstance(propriedades, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(configuracao.getUser(), configuracao.getPassword());
                }
            });
            session.setDebug(true);
            return session;
        }else{
            return Session.getInstance(propriedades);
        }
    }

}
