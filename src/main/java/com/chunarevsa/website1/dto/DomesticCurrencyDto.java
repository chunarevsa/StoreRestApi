package com.chunarevsa.website1.dto;

import com.chunarevsa.website1.entity1.DomesticCurrency;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DomesticCurrencyDto {

	private String title;
	private String cost;

	public DomesticCurrencyDto() {
	}

	public DomesticCurrencyDto(String title, String cost) {
		this.title = title;
		this.cost = cost;
	}

	public static DomesticCurrencyDto fromUser (DomesticCurrency currency) {
		DomesticCurrencyDto currencyDto = new DomesticCurrencyDto();
		currencyDto.setTitle(currency.getTitle());
		currencyDto.setCost(currency.getCost());
		return currencyDto;
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

	@Override
	public String toString() {
		return "{" +
			" title='" + getTitle() + "'" +
			", cost='" + getCost() + "'" +
			"}";
	}

}
