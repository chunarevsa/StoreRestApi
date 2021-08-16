package com.chunarevsa.Website.Entity;


import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Price 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String amount;
	private String currency; 
	
	// Рабочий вар
	@ManyToOne
	@JoinColumn(name = "Price_price")
	public Price price;

	public Price getPrice() {
		return this.price;
	}
	public void setPrice(Price price) {
		this.price = price;
	}

	// Второй вар - не раб
	/* @ManyToOne
	@JoinColumn(name = "SetPrice")
	
	public Set<Price> price;

	public Set<Price> getPrice() {
		return this.price;
	}

	public void setPrice(Set<Price> price) {
		this.price = price;
	} */  

	// 3 вар - не раб

	/* @ManyToOne
	@JoinColumn(name = "Price_price_set")
	public Price price;

	public Price getPrice() {
		return this.price;
	}
	public void setPrice(Price price) {
		this.price = price;
	} 

	public Set<Price> setprice;

	public Set<Price> getsetPrice() {
		return this.setprice;
	}

	public void setPrice(Set<Price> setprice) {
		this.setprice = setprice;
	} */

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Price() {
	}

	public Price(String amount, String currency) {
		this.currency = currency;
		this.amount = amount;
	}
	
}
