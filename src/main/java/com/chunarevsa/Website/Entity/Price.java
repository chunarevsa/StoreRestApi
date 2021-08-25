package com.chunarevsa.Website.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Price {	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String amount;
	
	/* @OneToOne (cascade = CascadeType.ALL) 
	// Работает тольков при cacadeType.ALL
	@JoinColumn (name = "currency_code", referencedColumnName = "code") 
	private Currency currency;

	// code из Запроса проходит здесь 
	public Currency getCurrency() {
		Currency currency = this.currency;
		currency.setActive(true);
		currency.setCode("code");   
		// это я пытался понять где происходить созадние, 
		// путём последовательного изм данных

		return currency;

	}  */

	/* public void setCurrency(Currency currency) {
		this.currency = currency;
	} */


	@JsonIgnore
	@ManyToOne (cascade = CascadeType.ALL)
	private Item item;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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

	public Price() {
	}

	public Price(Price priceBody) {
		this.amount = priceBody.amount;
		// this.currency = priceBody.currency; 
		this.item = priceBody.item;
	}



}
