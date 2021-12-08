package com.chunarevsa.Website.dto;

import com.chunarevsa.Website.Entity.DomesticCurrency;
import com.chunarevsa.Website.Entity.Price;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceDto {
	
	private String cost;
	private String currencyTitle;

	public PriceDto() {
	}

	public PriceDto(String cost, String currencyTitle) {
		this.cost = cost;
		this.currencyTitle = currencyTitle;
	}

	public static PriceDto fromUser (Price price) {
		PriceDto priceDto = new PriceDto();
		priceDto.setCost(price.getCost());
		DomesticCurrency domesticCurrency = price.getDomesticCurrency();
		
		priceDto.setCurrencyTitle(domesticCurrency.getTitle());

		return priceDto;
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

	@Override
	public String toString() {
		return "{" +
			" cost='" + getCost() + "'" +
			", currencyTitle='" + getCurrencyTitle() + "'" +
			"}";
	}

}
