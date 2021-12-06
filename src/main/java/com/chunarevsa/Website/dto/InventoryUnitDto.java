package com.chunarevsa.Website.dto;

import com.chunarevsa.Website.Entity.InventoryUnit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InventoryUnitDto {

	private Long id;
	private String amountItems;
	private UserItemDto userItemDto;

	public InventoryUnitDto() {
	}

	public static InventoryUnitDto fromUser (InventoryUnit inventoryUnit) {
		InventoryUnitDto inventoryUnitDto = new InventoryUnitDto();
		inventoryUnitDto.setAmountItems(inventoryUnit.getAmountItems());
		inventoryUnitDto.setId(inventoryUnit.getId());
		inventoryUnitDto.setUserItemDto(UserItemDto.fromUser(inventoryUnit.getUserItem()));
		return inventoryUnitDto;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAmountItems() {
		return this.amountItems;
	}

	public void setAmountItems(String amountItems) {
		this.amountItems = amountItems;
	}

	public UserItemDto getUserItemDto() {
		return this.userItemDto;
	}

	public void setUserItemDto(UserItemDto userItemDto) {
		this.userItemDto = userItemDto;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", amountItems='" + getAmountItems() + "'" +
			", userItemDto='" + getUserItemDto() + "'" +
			"}";
	}
}
