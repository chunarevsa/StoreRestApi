package com.chunarevsa.Website.Entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Price extends Base {	
	
	private String amount;
	private String currencyCode;

	@JsonIgnore
	@ManyToOne 
	@JoinColumn (
		name = "item_id", 
		insertable = false, 
		updatable = false)
	private Item item;

	public Price() {}

	public Price(
				String amount, 
				String currencyCode,
				 Item item) {
		this.amount = amount;
		this.currencyCode = currencyCode;
		this.item = item;
	}

	public String getAmount() {return this.amount;}
	public void setAmount(String amount) {this.amount = amount;}

	public String getCurrencyCode() {return this.currencyCode;}
	public void setCurrencyCode(String currencyCode) {this.currencyCode = currencyCode;}

	public Item getItem() {return this.item;}
	public void setItem(Item item) {this.item = item;}

}
