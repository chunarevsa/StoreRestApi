package com.chunarevsa.website1.service;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.chunarevsa.website1.payload.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Value("${spring.mail.username}")
	private String username;

	@Value("${jwt.token.password.reset.duration}")
	private Long expirtion;

	private final JavaMailSender mailSender;

	@Autowired
	public MailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * Отправка сообщения с подтверждением
	 */
	public void sendMessageVerification (String emailTo, String emailConfirmationUrl) throws MessagingException {

		Mail mailMessage = new Mail();
		mailMessage.setFrom(username);
		mailMessage.setTo(emailTo);
		mailMessage.setSubject("Email Verification ");
		mailMessage.setMessage("Добро пожаловать в наш магазин! " + "\n Чтобы завершить регистрацию перейдите по ссылке \n " + "" + emailConfirmationUrl);
		send(mailMessage);
	}

	private void send(Mail mailMessage) throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(
			message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
		
		mimeMessageHelper.setTo(mailMessage.getTo());
		mimeMessageHelper.setText(mailMessage.getMessage(), true);
		mimeMessageHelper.setSubject(mailMessage.getSubject());
		mimeMessageHelper.setFrom(mailMessage.getFrom());
		mailSender.send(message);

	} 
}
