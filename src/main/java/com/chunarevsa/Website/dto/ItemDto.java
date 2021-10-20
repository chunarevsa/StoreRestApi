package com.chunarevsa.Website.dto;

import java.util.*;

import com.chunarevsa.Website.Entity.Item;
import com.chunarevsa.Website.Entity.Price;

public class ItemDto {
	
	private Long id; 

	private String sku;
	private String name;
	private String type;
	private String description;
	// private boolean active;
	private Set<Price> prices;

	public ItemDto() {}

	public static ItemDto toModel (Item item) {
		ItemDto itemModel = new ItemDto();
		itemModel.setId(item.getId());
		itemModel.setSku(item.getSku());
		itemModel.setName(item.getName());
		itemModel.setType(item.getType());
		itemModel.setDescription(item.getDescription());
		// itemModel.setActive(item.isActive());
		itemModel.setPrices(item.getPrices());;
		/* itemModel.setPrices(item.getPrices().stream()
				.map(PriceModel::priceModel).collect(Collectors.toSet())); */
		return itemModel;
	}

	public Long getId() {return this.id;}
	public void setId(Long id) {this.id = id;}

	public String getSku() {return this.sku;}
	public void setSku(String sku) {this.sku = sku;}

	public String getName() {return this.name;}
	public void setName(String name) {this.name = name;}

	public String getType() {return this.type;}
	public void setType(String type) {this.type = type;}

	public String getDescription() {return this.description;}
	public void setDescription(String description) {this.description = description;}

	/* public boolean isActive() {return this.active;}
	public boolean getActive() {return this.active;}
	public void setActive(boolean active) {this.active = active;} */

	public Set<Price> getPrices() {return this.prices;}
	public void setPrices(Set<Price> prices) {this.prices = prices;}

	

}
