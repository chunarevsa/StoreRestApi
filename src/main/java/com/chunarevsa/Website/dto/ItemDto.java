package com.chunarevsa.Website.dto;

import java.util.*;

import com.chunarevsa.Website.Entity.Item;
import java.util.stream.Collectors;

import lombok.Data;

@Data
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

}
