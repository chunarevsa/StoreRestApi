package com.chunarevsa.Website.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Items {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id; 
	private String sku, name, type, description, cost;

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
	} 

	// Конструкторы
	public Items () {
	}
		// Для Controller GUI
	public Items (String sku, String name,String type, String description, String cost) {
		this.sku = sku;
		this.cost = cost;
		this.type = type;
		this.name = name;
		this.description = description;
	}
		// Для ItemsController
	public Items (Items itemsBody) {
		this.sku = itemsBody.sku;
		this.cost = itemsBody.cost;
		this.type = itemsBody.type;
		this.name = itemsBody.name;
		this.description = itemsBody.description;
	}
}
