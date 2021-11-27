package com.chunarevsa.Website.dto;

import java.util.*;

import com.chunarevsa.Website.Entity.Item;
import java.util.stream.Collectors;


public class ItemDto {

	private Long id;
	private String sku;
	private String name;
	private String type;
	private String description;
	private Set<PriceDto> prices;

	public ItemDto() {}

	public static ItemDto toModel (Item item) {
		
		ItemDto itemDto = new ItemDto();
		itemDto.setId(item.getId());
		itemDto.setSku(item.getSku());
		itemDto.setName(item.getName());
		itemDto.setType(item.getType());
		itemDto.setDescription(item.getDescription());
		itemDto.setPrices(item.getPrices().stream()
				.map(PriceDto :: toModel).collect(Collectors.toSet()));
		
		return itemDto;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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

	public Set<PriceDto> getPrices() {
		return this.prices;
	}

	public void setPrices(Set<PriceDto> prices) {
		this.prices = prices;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", sku='" + getSku() + "'" +
			", name='" + getName() + "'" +
			", type='" + getType() + "'" +
			", description='" + getDescription() + "'" +
			", prices='" + getPrices() + "'" +
			"}";
	}


}
