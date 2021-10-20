package com.chunarevsa.Website.dto;

import com.chunarevsa.Website.Entity.Price;

public class PriceDto {
	
	private Long id;
	private String amount;
	private String currencyCode;

	public static PriceDto priceModel (Price price) {
		PriceDto priceModel = new PriceDto();
		priceModel.setId(price.getId());
		priceModel.setAmount(price.getAmount());
		priceModel.setCurrencyCode(price.getCurrencyCode());
		return priceModel;
	}

	public PriceDto() {}

	public Long getId() {return this.id;}
	public void setId(Long id) {this.id = id;}

	public String getAmount() {return this.amount;}
	public void setAmount(String amount) {this.amount = amount;}

	public String getCurrencyCode() {return this.currencyCode;}
	public void setCurrencyCode(String currencyCode) {this.currencyCode = currencyCode;}

}
