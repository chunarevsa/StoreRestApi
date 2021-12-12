package com.chunarevsa.Website.payload;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Item Request", description = "Item Request")
public class ItemRequest {

	@NotNull(message = "Item name cannot be null")
	@NotBlank(message = "Item name cannot be blank")
	@ApiModelProperty(value = "Название Item", required = true, allowableValues = "NonEmpty String")
	private String name;

	@NotNull(message = "Type cannot be null")
	@NotBlank(message = "Type cannot be blank")
	@ApiModelProperty(value = "Тип Item", required = true, allowableValues = "NonEmpty String")
	private String type;

	@NotBlank(message = "Description cannot be blank")
	@ApiModelProperty(value = "Описание Item", required = true, allowableValues = "NonEmpty String")
	private String description;

	@NotNull(message = "Whether the Item will be active or not")
	@ApiModelProperty(value = "Указывает будет ли Item активным", required = true, dataType = "boolean", allowableValues = "true, false")
	private Boolean active;

	@NotNull(message = "One price must be specified")
	@NotEmpty(message = "One price must be specified")
	@ApiModelProperty(value = "Цены во внутренней валюте для Item", required = true, allowableValues = "NonEmpty String")
	private Set<PriceRequest> prices;

	public ItemRequest() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Set<PriceRequest> getprices() {
		return this.prices;
	}

	public void setprices(Set<PriceRequest> prices) {
		this.prices = prices;
	}

	@Override
	public String toString() {
		return "{" +
				" name='" + getName() + "'" +
				", type='" + getType() + "'" +
				", description='" + getDescription() + "'" +
				", active='" + isActive() + "'" +
				", prices='" + getprices() + "'" +
				"}";
	}

}
