package com.chunarevsa.Website.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.chunarevsa.Website.Exception.AllException;
import com.chunarevsa.Website.Exception.NotFound;
import com.chunarevsa.Website.repo.CurrencyRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@Entity
public class Price {	
	
	@Autowired
	@Transient
	private CurrencyRepository currencyRepository;
	public Price (CurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String amount;
	private String currencyCode;

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) throws AllException {
		
		/* Currency cur = currencyRepository.findByCode(currencyCode);
		if (cur.getCode().isEmpty() == true) {
			this.currencyCode = "Ошибка";
			throw new NotFound(HttpStatus.NOT_FOUND);
		} */

		this.currencyCode = currencyRepository.findByCode(currencyCode)

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
		
		Currency cur = currencyRepository.findByCode(priceBody.currencyCode);
		if (cur.getCode().isEmpty() == true) {
			this.currencyCode = "Ошибка";
			throw new NotFound(HttpStatus.NOT_FOUND);
		}

		

		/* Boolean boo = currencyRepository.findByCodOptional(priceBody.currencyCode).isPresent();
		if (boo == false) {
			throw new NotFound(HttpStatus.NOT_FOUND);
		} */

		//this.currencyCode = priceBody.currencyCode;
		this.item = priceBody.item;
	}
}
