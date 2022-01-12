package com.chunarevsa.website.entity;

public class Skin extends Item {
	
	private String description;

	public Skin(String description) {
		this.description = description;
		super.setType("Weapon");
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
