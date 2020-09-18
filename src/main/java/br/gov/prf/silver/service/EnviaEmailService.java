package br.gov.prf.silver.service;

import javax.mail.Message;
import javax.mail.MessagingException;

/**
 * Service Interface.
 */
public interface EnviaEmailService {

	void gerandoEmail(String destinatarios, String assunto, String conteudo) throws MessagingException;
	void enviaEmail(Message mensagem) throws MessagingException;
}
