package com.chunarevsa.website1.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Price Request", description = "Price Request")
public class PriceRequest {
	
	@NotNull(message = "Cost cannot be null")
	@NotBlank(message = "Cost cannot be blank")
	@ApiModelProperty(value = "Стоимость Item", required = true, allowableValues = "NonEmpty String")
	private String cost;

	@NotNull(message = "Currency cannot be null")
	@NotBlank(message = "Currency cannot be blank")
	@ApiModelProperty(value = "Внутреняя валюта", required = true, allowableValues = "NonEmpty String")
	private String currency;

	@NotNull(message = "Whether the price will be active or not")
	@ApiModelProperty(value = "Указывает будет ли Price активна", required = true,
	dataType = "boolean", allowableValues = "true, false")
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
