package com.chunarevsa.Website.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Price 
{	

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

	// Первый вар
	/* @ManyToOne 
	private Item item;

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	} */

	// Второй вар
	@ManyToOne (fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "item_id", referencedColumnName = "id", nullable = false)
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

	public Price(Price priceBody) {
		this.amount = priceBody.amount;
		this.currency_Str = priceBody.currency_Str;
		this.item = priceBody.item;
	}
}
