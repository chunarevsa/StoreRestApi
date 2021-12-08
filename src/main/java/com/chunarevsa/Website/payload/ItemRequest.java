package com.chunarevsa.Website.payload;

import java.util.Set;

public class ItemRequest {
	
	private String name;
	private String type;
	private String description;
	private Boolean active;
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
