package com.chunarevsa.Website.Entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Price extends Base {	
	
	private String amount;
	private String currencyCode;

	@JsonIgnore
	@ManyToOne 
	@JoinColumn (
		name = "item_id", 
		insertable = false, 
		updatable = false)
	private Item item;

}
