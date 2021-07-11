package com.chunarevsa.Website.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Items {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id; 
	private String sku, name, type, description;
	private int cost;

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

	public int getCost() {
		return this.cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	// Конструкторы
	public Items () {
	}

	public Items (String sku, String name,String type, String description, int cost) {
		this.sku = sku;
		this.type = type;
		this.name = name;
		this.description = description;
		this.cost = cost;
	}

	public Items (Items itemsBody) {
		this.sku = itemsBody.sku;
		this.type = itemsBody.type;
		this.name = itemsBody.name;
		this.description = itemsBody.description;
		this.cost = itemsBody.cost;
	}
}
