package com.chunarevsa.Website.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Price 
{	
	// Рабочий вар
	// Второй вар - не раб	
	/* @ManyToOne
	@JoinColumn(name = "SetPrice")
	
	public Set<Price> price;

	public Set<Price> getPrice() {
		return this.price;
	}

	public void setPrice(Set<Price> price) {
		this.price = price;
	}  */ 

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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String amount;
	private String currency_Str;

	public String getCurrency_Str() {
		return this.currency_Str;
	}

	public void setCurrency_Str(String currency_Str) {
		this.currency_Str = currency_Str;
	}

	/* @OneToOne // рабочая - при Currency currency
	private Currency currency; 

	public Currency getCurrency() {
		return this.currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	} */

	// вар 4 - раб
	@ManyToOne (cascade = CascadeType.ALL)
	@JoinColumn (name = "item_id", referencedColumnName = "id")
	private Item item;

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

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

	public Price() {
	}

	public Price(Item item, String currency_Str) {
		this.currency_Str = currency_Str;
		this.item = item;
	}
	
	public Price(String currency_Str) {
		this.currency_Str = currency_Str;
	}
}
