package com.chunarevsa.Website.Entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table (name = "items")
public class Item extends Base {

	private String sku;
	private String name;
	private String type;
	private String description;

	@OneToMany
	@JoinColumn (name = "item_id")
	private Set<Price> prices;

	public Item() {}
	public Item(
				String sku, 
				String name, 
				String type, 
				String description, 
				Set<Price> prices) {
		this.sku = sku;
		this.name = name;
		this.type = type;
		this.description = description;
		this.prices = prices;
	}

	public String getSku() {return this.sku;}
	public void setSku(String sku) {this.sku = sku;}

	public String getName() {return this.name;}
	public void setName(String name) {this.name = name;}

	public String getType() {return this.type;}
	public void setType(String type) {this.type = type;}

	public String getDescription() {return this.description;}
	public void setDescription(String description) {this.description = description;}

	public Set<Price> getPrices() {return this.prices;}
	public void setPrices(Set<Price> prices) {this.prices = prices;}

}
