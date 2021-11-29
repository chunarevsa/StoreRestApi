package com.chunarevsa.Website.dto;

// Ввод цены
public class PriceRequest {
	
	private String cost;
	private String currencyTitle;
	private Boolean active;

	public PriceRequest() {
	}

	public PriceRequest(String cost, String currencyTitle, Boolean active) {
		this.cost = cost;
		this.currencyTitle = currencyTitle;
		this.active = active;
	}

	public String getCost() {
		return this.cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getCurrencyTitle() {
		return this.currencyTitle;
	}

	public void setCurrencyTitle(String currencyTitle) {
		this.currencyTitle = currencyTitle;
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


	@Override
	public String toString() {
		return "{" +
			" cost='" + getCost() + "'" +
			", currencyTitle='" + getCurrencyTitle() + "'" +
			", active='" + isActive() + "'" +
			"}";
	}




}
