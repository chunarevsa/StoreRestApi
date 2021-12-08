package com.chunarevsa.Website.payload;

// Ввод цены
public class PriceRequest {
	
	private String cost;
	private String currency;
	private Boolean active;

	public PriceRequest() {
	}

	public PriceRequest(String cost, String currency, Boolean active) {
		this.cost = cost;
		this.currency = currency;
		this.active = active;
	}

	public String getCost() {
		return this.cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
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


	@Override
	public String toString() {
		return "{" +
			" cost='" + getCost() + "'" +
			", currencyTitle='" + getCurrency() + "'" +
			", active='" + isActive() + "'" +
			"}";
	}




}
