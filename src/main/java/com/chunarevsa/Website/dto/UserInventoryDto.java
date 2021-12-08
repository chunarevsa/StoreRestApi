package com.chunarevsa.Website.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.entity.UserInventory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInventoryDto {

	private Set<InventoryUnitDto> inventoryUnit;

	public UserInventoryDto() {
	}

	public static UserInventoryDto fromUser(UserInventory userInventory) {
		UserInventoryDto userInventoryDto = new UserInventoryDto();
		Set<InventoryUnitDto> collect = userInventory.getInventoryUnit().stream()
				.map(InventoryUnitDto::fromUser).collect(Collectors.toSet());
		userInventoryDto.setInventoryUnit(collect);

		return userInventoryDto;
	}

	public Set<InventoryUnitDto> getInventoryUnit() {
		return this.inventoryUnit;
	}

	public void setInventoryUnit(Set<InventoryUnitDto> inventoryUnitDto) {
		this.inventoryUnit = inventoryUnitDto;
	}

	@Override
	public String toString() {
		return "{" +
			" inventoryUnitDto='" + getInventoryUnit() + "'" +
			"}";
	}

}
