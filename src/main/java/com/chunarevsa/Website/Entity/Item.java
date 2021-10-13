package com.chunarevsa.Website.Entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 

	private String sku;
	private String name;
	private String type;
	private String description;
	private boolean active;

	@OneToMany
	@JoinColumn (name = "item_id")
	private Set<Price> prices;
	
	// Конструкторы
	public Item (Item bodyItem) {
		this.sku = bodyItem.sku;
		this.type = bodyItem.type;
		this.name = bodyItem.name;
		this.description = bodyItem.description;
		this.active = bodyItem.active;
		this.prices = bodyItem.prices;
	} 

	public Item () {}

	// Getter and Setter
	public Long getId() {return this.id; }
	public void setId(Long id) {this.id = id;}

	public String getSku() {return this.sku;}
	public void setSku(String sku) {this.sku = sku;}

	public String getName() {return this.name;}
	public void setName(String name) {this.name = name;}

	public String getType() {return this.type;}
	public void setType(String type) {this.type = type;}

	public String getDescription() {return this.description;}
	public void setDescription(String description) {this.description = description;}

	public boolean isActive() {return this.active;}
	public boolean getActive() {return this.active;}
	public void setActive(boolean active) {this.active = active;}

	public Set<Price> getPrices() {return this.prices;}
	public void setPrices(Set<Price> prices) {this.prices = prices;}

}
