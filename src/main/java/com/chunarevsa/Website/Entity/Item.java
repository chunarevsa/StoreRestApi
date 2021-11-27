package com.chunarevsa.Website.Entity;

import java.util.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table (name = "ITEM")
public class Item extends Base {

	@Id
	@Column (name = "ITEM_ID")
	@GeneratedValue (strategy =  GenerationType.IDENTITY)
	private Long id;
	
	private String sku;
	private String name;
	private String type;
	private String description;
	private Boolean active;

	@OneToMany // вынести в отдельную таблицу - доделать 
	@JoinColumn (name = "ITEM_ID")
	private Set<Price> prices;

	public Item() {
		super();
	}
	public Item(
				Long id,
				String sku, 
				String name, 
				String type, 
				String description,
				Boolean active, 
				Set<Price> prices) {
		this.sku = sku;
		this.name = name;
		this.type = type;
		this.description = description;
		this.active = active;
		this.prices = prices;
	}

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

	public Boolean isActive() {
		return this.active;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Set<Price> getPrices() {
		return this.prices;
	}

	public void setPrices(Set<Price> prices) {
		this.prices = prices;
	}
	
	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", sku='" + getSku() + "'" +
			", name='" + getName() + "'" +
			", type='" + getType() + "'" +
			", description='" + getDescription() + "'" +
			", active='" + isActive() + "'" +
			", prices='" + getPrices() + "'" +
			"}";
	}


}
