package com.chunarevsa.Website.Entity.token;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.chunarevsa.Website.Entity.User;


@Entity(name = "VERIFICATION_TOKEN")
public class EmailVerificationToken {

	@Id
	@Column (name = "TOKEN_ID")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TOKEN", nullable = false, unique = true)
	private String token;

	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
   @JoinColumn(nullable = false, name = "USER_ID")
	private User user;

	@Column(name = "TOKEN_STATUS")
	@Enumerated (EnumType.STRING)
	private TokenStatus tokenStatus;

	@Column(name = "EXPIRY_DT", nullable = false)
	private Instant expiryDate;

	public EmailVerificationToken() {}

	public EmailVerificationToken(EmailVerificationToken verificationToken) {
		this.id = verificationToken.id;
		this.token = verificationToken.token;
		this.user = verificationToken.user;
		this.tokenStatus = verificationToken.tokenStatus;
		this.expiryDate = verificationToken.expiryDate;
	}

	public void setConfirmedStatus() {
		setTokenStatus(TokenStatus.STATUS_CONFIRMED);
	}

	public Long getId() {return this.id;}
	public void setId(Long id) {this.id = id;}

	public String getToken() {return this.token;}
	public void setToken(String token) {this.token = token;}

	public User getUser() {return this.user;}
	public void setUser(User user) {this.user = user;}

	public TokenStatus getTokenStatus() {return this.tokenStatus;}
	public void setTokenStatus(TokenStatus tokenStatus) {this.tokenStatus = tokenStatus;}

	public Instant getExpiryDate() {return this.expiryDate;}
	public void setExpiryDate(Instant expiryDate) {this.expiryDate = expiryDate;}

}
