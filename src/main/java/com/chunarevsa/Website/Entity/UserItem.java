package com.chunarevsa.Website.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

// USER_ITEM принадлежащие конкретному User через UserInventory/InventoryUnit
// По сути это копия ITEM, но без цен (т.е без возможности его купить другим пользователем). 
// Изменяя ITEM USER_ITEM не будет меняться
@Entity
@Table(name = "USER_ITEM")
public class UserItem extends Base {
	
	@Id
	@Column (name = "USER_ITEM_ID")
	@GeneratedValue(strategy =  GenerationType.SEQUENCE, generator = "item_seq")
	@SequenceGenerator(name = "item_seq", allocationSize = 1)
	private Long id;

	@Column(name = "ITEM_ID", nullable = false)
	private Long itemId;

	@Column(name = "NAME", nullable = false)
	private String name;

	@Column(name = "TYPE", nullable = false)
	private String type;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "IS_ACTIVE", nullable = false)
	private Boolean active;

	@OneToOne(optional = false, mappedBy = "userItem")
	private InventoryUnit inventoryUnit;


	public UserItem() {
		super();
	}

	public UserItem(Long id, Long itemId, String name, String type, String description, Boolean active, InventoryUnit inventoryUnit) {
		this.id = id;
		this.itemId = itemId;
		this.name = name;
		this.type = type;
		this.description = description;
		this.active = active;
		this.inventoryUnit = inventoryUnit;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getItemId() {
		return this.itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
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

	public InventoryUnit getInventoryUnit() {
		return this.inventoryUnit;
	}

	public void setInventoryUnit(InventoryUnit inventoryUnit) {
		this.inventoryUnit = inventoryUnit;
	}



} 
