package com.chunarevsa.website1.dto;

import com.chunarevsa.website1.entity1.Item;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDtoFromUnit {
	
	private Long id;
	private String name;
	private String type;
	private String description;

	public ItemDtoFromUnit() {}

	public static ItemDtoFromUnit fromUnit (Item item) {
		ItemDtoFromUnit itemDtoFromUnit = new ItemDtoFromUnit();
		itemDtoFromUnit.setId(item.getId());
		itemDtoFromUnit.setName(item.getName());
		itemDtoFromUnit.setType(item.getType());
		itemDtoFromUnit.setDescription(item.getDescription());
		return itemDtoFromUnit;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", name='" + getName() + "'" +
			", type='" + getType() + "'" +
			", description='" + getDescription() + "'" +
			"}";
	}
}
