package com.chunarevsa.Website.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;


@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UniqueCode", columnNames = { "code"}) })
public class Currency extends Base {

	@Column(unique=true)
	private String code;	

	public Currency() {}
	public Currency(String code) {this.code = code;}

	public String getCode() {return this.code;}
	public void setCode(String code) {this.code = code;}

}
