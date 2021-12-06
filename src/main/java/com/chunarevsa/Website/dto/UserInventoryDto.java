package com.chunarevsa.Website.dto;

import java.util.Set;
import java.util.stream.Collectors;

import com.chunarevsa.Website.Entity.UserInventory;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInventoryDto {

	private Set<InventoryUnitDto> inventoryUnitDto;

	public UserInventoryDto() {
	}

	public static UserInventoryDto fromUser(UserInventory userInventory) {
		UserInventoryDto userInventoryDto = new UserInventoryDto();
		Set<InventoryUnitDto> collect = userInventory.getInventoryUnit().stream()
				.map(InventoryUnitDto::fromUser).collect(Collectors.toSet());
		userInventoryDto.setInventoryUnitDto(collect);

		return userInventoryDto;
	}

	public Set<InventoryUnitDto> getInventoryUnitDto() {
		return this.inventoryUnitDto;
	}

	public void setInventoryUnitDto(Set<InventoryUnitDto> inventoryUnitDto) {
		this.inventoryUnitDto = inventoryUnitDto;
	}

	@Override
	public String toString() {
		return "{" +
			" inventoryUnitDto='" + getInventoryUnitDto() + "'" +
			"}";
	}

}
