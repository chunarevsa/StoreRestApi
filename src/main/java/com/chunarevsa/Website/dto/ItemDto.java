package com.chunarevsa.Website.dto;

import java.util.*;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;
import java.util.stream.Collectors;

import lombok.Data;

@Data
public class ItemDto {

	private Long id; 

	private String sku;
	private String name;
	private String type;
	private String description;
	// private Set<PriceDto> prices;
	private Set<Price> prices;

	public ItemDto() {}

	public static ItemDto toModel (Item item) {
		
		ItemDto itemDto = new ItemDto();
		itemDto.setId(item.getId());
		itemDto.setSku(item.getSku());
		itemDto.setName(item.getName());
		itemDto.setType(item.getType());
		itemDto.setDescription(item.getDescription());

		System.out.println("----------------------Здесь 2-----------------------------------------------");
		itemDto.setPrices(item.getPrices());
		/* itemModel.setPrices(item.getPrices().stream()
				.map(PriceDto :: priceDto).collect(Collectors.toSet()));  */
		/* itemDto.setPrices(item.getPrices().stream()
				.map(PriceDto :: toModel).collect(Collectors.toSet())); */
		System.out.println("----------------------Здесь 3-----------------------------------------------");
		
		
		return itemDto;
	}

}
