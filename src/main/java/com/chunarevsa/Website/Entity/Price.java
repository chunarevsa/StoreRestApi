package com.chunarevsa.Website.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Price extends Base {	

	@Id
	@Column (name = "PRICE_ID")
	@GeneratedValue (strategy =  GenerationType.IDENTITY)
	private Long id;
	
	private String amount;
	private String currencyCode;
	private Boolean active;

	@JsonIgnore
	@ManyToOne 
	@JoinColumn (name = "ITEM_ID", insertable = false, updatable = false)
	private Item item;

	public Price() {
		super();
	}
	public Price(
				Long id,
				String amount, 
				String currencyCode,
				Boolean active,
				Item item) {
		this.id = id;
		this.amount = amount;
		this.currencyCode = currencyCode;
		this.active = active;
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

	public String getCurrencyCode() {
		return this.currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Boolean isActive() {
		return this.active;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Item getItem() {
		return this.item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", amount='" + getAmount() + "'" +
			", currencyCode='" + getCurrencyCode() + "'" +
			", active='" + isActive() + "'" +
			", item='" + getItem() + "'" +
			"}";
	}


	

}
