package com.chunarevsa.Website.payload;

public class DomesticCurrencyRequest {

	private String title;
	private String cost;
	private boolean active;

	public DomesticCurrencyRequest() {
	}

	public DomesticCurrencyRequest(String title, String cost, boolean active) {
		this.title = title;
		this.cost = cost;
		this.active = active;
	}
	
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCost() {
		return this.cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "{" +
			" title='" + getTitle() + "'" +
			", cost='" + getCost() + "'" +
			", active='" + isActive() + "'" +
			"}";
	}

}
