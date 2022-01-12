package com.chunarevsa.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "PRICE")
public class Price extends DateAudit {	

	@Id
	@Column(name = "PRICE_ID")
	@GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "price_seq")
	@SequenceGenerator(name = "price_seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "COST")
	private String cost;

	@OneToOne
	@JoinColumn(name = "CURRENCY_ID", referencedColumnName = "CURRENCY_ID")
	private Currency currency;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;

	@JsonIgnore
	@ManyToOne (fetch = FetchType.LAZY)
	@JoinColumn (name = "ITEM_ID", insertable = false, updatable = false)
	private Item item;

	public Price() {
		super();
	}

	public Price(Long id, String cost, Currency currency, Boolean active, Item item) {
		this.id = id;
		this.cost = cost;
		this.currency = currency;
		this.active = active;
		this.item = item;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCost() {
		return this.cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}


	public Currency getCurrency() {
		return this.currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
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

}
