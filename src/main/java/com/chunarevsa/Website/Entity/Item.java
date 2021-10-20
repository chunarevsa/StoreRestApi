package com.chunarevsa.Website.Entity;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "items")
@Data
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; 

	private String sku;
	private String name;
	private String type;
	private String description;
	private Status status;

	@OneToMany
	@JoinColumn (name = "item_id")
	private Set<Price> prices;

}
