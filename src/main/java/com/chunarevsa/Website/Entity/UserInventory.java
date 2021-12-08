package com.chunarevsa.Website.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "INVENTORY")
public class UserInventory extends DateAudit {
	
	@Id
	@Column(name = "INVENTORY_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_seq")
	@SequenceGenerator(name = "inventory_seq", allocationSize = 1)
	private Long id;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "INVENTORY_ID")
	private Set<InventoryUnit> inventoryUnit = new HashSet<>();

	@JsonIgnore
	@OneToOne(optional = false, mappedBy = "userInventory")
	private User user;

	public UserInventory() {
		super();
	}

	public UserInventory(Long id, Set<InventoryUnit> inventoryUnit, User user) {
		this.id = id;
		this.inventoryUnit = inventoryUnit;
		this.user = user;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<InventoryUnit> getInventoryUnit() {
		return this.inventoryUnit;
	}

	public void setInventoryUnit(Set<InventoryUnit> inventoryUnit) {
		this.inventoryUnit = inventoryUnit;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", inventoryUnit='" + getInventoryUnit() + "'" +
			", user='" + getUser() + "'" +
			"}";
	}

}
