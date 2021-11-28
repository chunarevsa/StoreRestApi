package com.chunarevsa.Website.dto;

import com.chunarevsa.Website.Entity.Price;

public class PriceDto {
	
	private Long id;
	private String amount;
	private String currencyTitle;

	public PriceDto() {
	}

	public PriceDto(Long id, String amount, String currencyTitle) {
		this.id = id;
		this.amount = amount;
		this.currencyTitle = currencyTitle;
	}

	public static PriceDto toModel (Price price) {
		PriceDto priceModel = new PriceDto();
		priceModel.setId(price.getId());
		//priceModel.setAmount(price.getAmount());
		//priceModel.setCurrencyTitle(price.getCurrencyTitle());
		return priceModel;
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

	public String getCurrencyTitle() {
		return this.currencyTitle;
	}

	public void setCurrencyTitle(String currencyTitle) {
		this.currencyTitle = currencyTitle;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", amount='" + getAmount() + "'" +
			", currencyTitle='" + getCurrencyTitle() + "'" +
			"}";
	}


}
