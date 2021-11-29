package com.chunarevsa.Website.dto;

public class ItemRequest {
	
	private String name;
	private String type;
	private String description;
	private Boolean active;
	private String cost;

	public ItemRequest() {
	}

	public ItemRequest(String name, String type, String description, Boolean active, String cost) {
		this.name = name;
		this.type = type;
		this.description = description;
		this.active = active;
		this.cost = cost;
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

	public String getCost() {
		return this.cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		return "{" +
			" name='" + getName() + "'" +
			", type='" + getType() + "'" +
			", description='" + getDescription() + "'" +
			", active='" + isActive() + "'" +
			", cost='" + getCost() + "'" +
			"}";
	}


}
