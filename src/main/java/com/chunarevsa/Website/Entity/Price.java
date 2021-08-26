package com.chunarevsa.Website.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.repo.CurrencyRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Entity
public class Price {	
	
	@Transient
	@JsonIgnore
	@Autowired
	private CurrencyRepository currencyRepository;
	public Price (CurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}


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

	private String currencyCode;

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) throws AllException {
		Boolean trt = currencyRepository.findByCode(currencyCode).isEmpty();
			if (trt == true) {
				throw new NotFound(HttpStatus.NOT_FOUND);
			}

		this.currencyCode = currencyCode;

	}


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

	public Price(Price priceBody) throws AllException {
		this.amount = priceBody.amount;
		try {
			Boolean trt = currencyRepository.findByCode(priceBody.currencyCode).isEmpty();
			if (trt == true) {
				throw new NotFound(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		}

		this.currencyCode = priceBody.currencyCode;
		this.item = priceBody.item;
	}



}
