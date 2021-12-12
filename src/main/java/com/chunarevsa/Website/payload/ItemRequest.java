package com.chunarevsa.Website.payload;

import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;


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
	@ApiModelProperty(value = "Whether the Item will be active or not", required = true,
            dataType = "boolean", allowableValues = "true, false")
	private Boolean active;
	
	@NotNull(message = "Необходимо указать будет ли Item активен")
	@NotEmpty(message = "Необходимо указать хотя бы одну цену для Item")
	@ApiModelProperty(value = "Цены во внутренней валюте для Item", required = true, allowableValues = "NonEmpty String")
	private Set<PriceRequest> pricies;

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

	public Set<PriceRequest> getPricies() {
		return this.pricies;
	}

	public void setPricies(Set<PriceRequest> pricies) {
		this.pricies = pricies;
	}

	@Override
	public String toString() {
		return "{" +
			" name='" + getName() + "'" +
			", type='" + getType() + "'" +
			", description='" + getDescription() + "'" +
			", active='" + isActive() + "'" +
			", pricies='" + getPricies() + "'" +
			"}";
	}

}
