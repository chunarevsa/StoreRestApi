package com.chunarevsa.Website.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueCode", columnNames = { "code"}) })
public class Currency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id; 
	
	private boolean active;

	@Column(unique=true)
	private String code;	

	public Currency() {}
	
	public Currency(Currency currencyBody) {
		this.code = currencyBody.code;
		this.active = currencyBody.active;
	}

	public Long getId() {return this.id;}
	public void setId(Long id) {this.id = id;}

	public String getCode() {return this.code;}
	public void setCode(String code) {this.code = code;}
	
	public boolean isActive() {return this.active;}
	public boolean getActive() {return this.active;}
	public void setActive(boolean active) {this.active = active;}
	
}
