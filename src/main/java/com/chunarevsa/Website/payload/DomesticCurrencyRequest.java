package com.chunarevsa.Website.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Currency Request", description = "Currency Request")
public class DomesticCurrencyRequest {

	@NotNull(message = "Currency title cannot be null")
	@NotBlank(message = "Currency title cannot be blank")
	@ApiModelProperty(value = "Название валюты", required = true, allowableValues = "NonEmpty String")
	private String title;

	@NotNull(message = "Cost cannot be null")
	@NotBlank(message = "Cost title cannot be blank")
	@ApiModelProperty(value = "Стоимость валюты в $", required = true, allowableValues = "NonEmpty String")
	private String cost;

	@NotNull(message = "Whether the currency will be active or not")
	@ApiModelProperty(value = "Указывает будет ли Currency активным", required = true,
            dataType = "boolean", allowableValues = "true, false")
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
