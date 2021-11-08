package com.chunarevsa.Website.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity 
@Table(name = "CURRENCY",
	uniqueConstraints = { 
		@UniqueConstraint(name = "UniqueCode", columnNames = { "code"}) })
public class Currency extends Base {

	@Id
	@Column (name = "CURRENCY_ID")
	@GeneratedValue (strategy =  GenerationType.IDENTITY)
	private Long id;

	@Column(unique=true)
	private String code;	

	private Boolean active;

	public Currency() {
		super();
	}
	public Currency(
					Long id,
					String code,
					Boolean active) {
		this.id = id;
		this.code = code;
		this.active = active;
	}

	public Long getId() {return this.id;}
	public void setId(Long id) {this.id = id;}

	public String getCode() {return this.code;}
	public void setCode(String code) {this.code = code;}

	public Boolean isActive() {return this.active;}
	public Boolean getActive() {return this.active;}
	public void setActive(Boolean active) {this.active = active;}

}
