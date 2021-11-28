package com.chunarevsa.Website.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

//  Внутренняя вертуальная валюта - Gold, Silver...
@Entity 
@Table(name = "DOMESTIC_CURRENCY",uniqueConstraints = { 
		@UniqueConstraint(name = "UniqueCode", columnNames = { "DOMESTIC_CURRENCY_TITLE"}) })
public class DomesticCurrency extends Base {

	@Id
	@Column (name = "DOMESTIC_CURRENCY_ID")
	@GeneratedValue (strategy =  GenerationType.SEQUENCE, 
						generator = "domestic_currency_seq")
	@SequenceGenerator(name = "domestic_currency_seq", allocationSize = 1)
	private Long id;

	@Column(name = "DOMESTIC_CURRENCY_TITLE", unique=true)
	private String title;

	// Цена вертуальной валюты в USD
	@Column(name = "DOMESTIC_CURRENCY_PRICE", nullable = false)
	private String cost; 

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;

	public DomesticCurrency() {
		super();
	}

	public DomesticCurrency(Long id, String title, String cost, Boolean active) {
		this.id = id;
		this.title = title;
		this.cost = cost;
		this.active = active;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getCost() {
		return this.cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", title='" + getTitle() + "'" +
			", cost='" + getCost() + "'" +
			", active='" + isActive() + "'" +
			"}";
	}

}
