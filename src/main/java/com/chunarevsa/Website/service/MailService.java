package com.chunarevsa.Website.service;

import java.nio.charset.StandardCharsets;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.chunarevsa.Website.Entity.Mail;

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
	
	public void sendMessageVerification (String emailTo, String emailConfirmationUrl) throws MessagingException {
		System.out.println("sendMessageVerification");
		//SimpleMailMessage mailMessage = new SimpleMailMessage();
		Mail mailMessage = new Mail();

		mailMessage.setFrom(username);
		mailMessage.setTo(emailTo);
		mailMessage.setSubject("Email Verification ");
		mailMessage.setMessage("Добро пожаловать в наш магазин! \n Чтобы завершить регистрацию перейдите по ссылке \n " + emailConfirmationUrl);
		send(mailMessage);
		System.out.println("sendMessageVerification - ok");
	}

	private void send(Mail mailMessage) throws MessagingException {
		System.out.println("send");
		MimeMessage message = mailSender.createMimeMessage();
		System.out.println("1");
		MimeMessageHelper mimeMessageHelper =
				 new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				 StandardCharsets.UTF_8.name());
				 System.out.println("2");		 
		
		mimeMessageHelper.setTo(mailMessage.getTo());
		//System.out.println(mailMessage.getTo());
		System.out.println("3");
		mimeMessageHelper.setText(mailMessage.getMessage(), true);
		System.out.println("4");
		mimeMessageHelper.setSubject(mailMessage.getSubject());
		mimeMessageHelper.setFrom(mailMessage.getFrom());
		System.out.println("5");
		mailSender.send(message);
		System.out.println("send - ok ");

	} 

	/* public void sendMessageVerification2 (String emailTo, String emailVerificationUrl) {
		
		Mail mail = new Mail();

		System.out.println(username);
		mail.setFrom(username);

		System.out.println(emailTo);
		mail.setTo(emailTo);

		mail.setSubject("Email Verification(Subject)");
		System.out.println("Email Verification(Subject)");

		System.out.println(message);
		mail.setText(message);

		mailSender.send(mail);

	}  */
}
