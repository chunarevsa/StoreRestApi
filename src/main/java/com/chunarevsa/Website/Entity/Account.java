package com.chunarevsa.Website.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "ACCOUNT")
public class Account extends Base {

	@Id
	@Column(name = "ACCOUNT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
	@SequenceGenerator(name = "account_seq", allocationSize = 1)
	private Long id;

	@Column(name = "AMOUNT")
	private String amount;

	@Column(name = "CURRENCY")
	private String currencyTitle;

	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "USER_ID", insertable = false, updatable = false)
	private User user;

	public Account() {
		super();
		this.amount = String.valueOf(0);
	}

	public Account(Long id, String amount, String currencyTitle, User user) {
		this.id = id;
		this.amount = amount;
		this.currencyTitle = currencyTitle;
		this.user = user;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getCurrencyTitle() {
		return this.currencyTitle;
	}

	public void setCurrencyTitle(String currencyTitle) {
		this.currencyTitle = currencyTitle;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", amount='" + getAmount() + "'" +
			", currencyTitle='" + getCurrencyTitle() + "'" +
			", user='" + getUser() + "'" +
			"}";
	}

}
