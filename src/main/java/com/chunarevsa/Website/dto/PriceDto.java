package com.chunarevsa.Website.dto;

import com.chunarevsa.Website.Entity.Price;

import lombok.Data;

@Data
public class PriceDto {
	
	private Long id;
	private String amount;
	private String currencyCode;

	public static PriceDto toModel (Price price) {
		PriceDto priceModel = new PriceDto();
		priceModel.setId(price.getId());
		priceModel.setAmount(price.getAmount());
		priceModel.setCurrencyCode(price.getCurrencyCode());
		return priceModel;
	}

}
