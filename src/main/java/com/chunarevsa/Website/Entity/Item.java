package com.chunarevsa.Website.Entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id; 
	private String sku, name, type, description;
	private String cost;
	private boolean active;
	
	Map<String, String> map1;

	public Map<String,String> getMap1() {
		return this.map1;
	}

	public void setMap1(String cost, String currency) {
		map1 = new HashMap<>();
		map1.put(cost, "value");
		this.map1 = map1;
	}


	// Getter and Setter
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

	public String getCost() {
		return this.cost;
	}

	public void setCost(String cost) {
		this.cost = cost;

		String costtt = cost;
		this.price = new String[2];
		price[0] = costtt;
		price[1] = "USD";
	} 

	public boolean isActive() {
		return this.active;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	// Конструкторы
	public Item () {
	}
		// Для Controller GUI
	public Item (String sku, String name,String type, String description, String cost) {
		this.sku = sku;
		this.cost = cost;
		this.type = type;
		this.name = name;
		this.description = description;
	}
		// Для ItemsController
	public Item (Item itemBody) {
		this.sku = itemBody.sku;
		this.cost = itemBody.cost;
		this.type = itemBody.type;
		this.name = itemBody.name;
		this.description = itemBody.description;
		this.active = itemBody.active;
	} 

}
