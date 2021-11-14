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
		System.out.println(username);

		mailMessage.setTo(emailTo);
		System.out.println(emailTo);

		mailMessage.setSubject("Email Verification ");
		System.out.println("subject is - Email Verification ");

		mailMessage.setMessage("Какой-то текст ");
		System.out.println("Какой-то текст ");

		//mailSender.send(mailMessage);

		send(mailMessage);
		System.out.println("sendMessageVerification - ok");
	}

	private void send(Mail mailMessage) throws MessagingException {
		System.out.println("send");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper =
				 new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				 StandardCharsets.UTF_8.name());
		
		mimeMessageHelper.setTo(mailMessage.getTo());
		mimeMessageHelper.setText(mailMessage.getMessage(), true);
		mimeMessageHelper.setSubject(mailMessage.getSubject());
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
