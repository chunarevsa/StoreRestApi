package com.chunarevsa.Website.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Currencies {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private Long id; // SKU

	private String sku, name, type, description;
	private int cost;

	// Getter and Setter
	public String getSku() {
		return this.sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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
	public Currencies() {
	}

	public Currencies(String name, String description, int cost) {
		this.name = name;
		this.description = description;
		this.cost = cost;
	}

		//Body
	public Currencies(Currencies newCurrency) {
		this.name = newCurrency.name;
		this.description = newCurrency.description;
		this.cost = newCurrency.cost;
		this.type = newCurrency.type;
	}


}