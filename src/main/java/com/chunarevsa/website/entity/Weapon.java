package com.chunarevsa.website.entity;

public class Weapon extends Item {

	private String description;

	public Weapon(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
