package com.chunarevsa.Website.Entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "INVENTORY_UNIT")
public class InventoryUnit extends DateAudit {
	
	@Id
	@Column(name = "UNIT_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unit_seq")
	@SequenceGenerator(name = "unit_seq", allocationSize = 1)
	private Long id;

	@Column(name = "AMOUNT_ITEMS")
	private String amountItems;

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "USER_ITEM_ID", unique = true)
	private UserItem userItem;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn (name = "INVENTORY_ID", insertable = false)
	private UserInventory userInventory;
	
	public InventoryUnit() {
		super();
	}

	public InventoryUnit(Long id, String amountItems, UserItem userItem, UserInventory userInventory) {
		this.id = id;
		this.amountItems = amountItems;
		this.userItem = userItem;
		this.userInventory = userInventory;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAmountItems() {
		return this.amountItems;
	}

	public void setAmountItems(String amountItems) {
		this.amountItems = amountItems;
	}

	public UserItem getUserItem() {
		return this.userItem;
	}

	public void setUserItem(UserItem userItem) {
		this.userItem = userItem;
	}

	public UserInventory getUserInventory() {
		return this.userInventory;
	}

	public void setUserInventory(UserInventory userInventory) {
		this.userInventory = userInventory;
	}

	@Override
	public String toString() {
		return "{" +
			" id='" + getId() + "'" +
			", amountItems='" + getAmountItems() + "'" +
			", userItem='" + getUserItem() + "'" +
			", userInventory='" + getUserInventory() + "'" +
			"}";
	}

} 
