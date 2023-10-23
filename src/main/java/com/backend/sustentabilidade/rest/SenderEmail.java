package com.backend.sustentabilidade.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class SenderEmail {

	@Autowired
	private JavaMailSender sender;
	
	public void enviar(String userEmail, String forum, String user) {
		SimpleMailMessage email = new SimpleMailMessage();
		email.setFrom("fs1898117@gmail.com");
		email.setTo(userEmail);
		email.setSubject("Nova Publicação no Futreen!");
		email.setText("Olá "+ user +". Temos novidades!\n\nHá uma nova publicação no fórum de discussão "+forum+" em que você está participando.");
		sender.send(email);
	}
}
