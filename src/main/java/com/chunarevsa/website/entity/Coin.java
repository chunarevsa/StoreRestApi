package com.chunarevsa.website.entity;

public class Coin extends Currency {

	private String description;

	public Coin(String description) {
		this.description = description;
		super.setType("Coin");
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
